package com.online.service.implementation;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.online.dto.Nominatores;
import com.online.dto.Officers;
import com.online.dto.Voters;
import com.online.helper.AES;
import com.online.helper.CloudinaryHelper;
import com.online.helper.MyEmailSender;
import com.online.repository.NominatoresRepository;
import com.online.repository.OfficersRepository;
import com.online.repository.VotersRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Service
public class OfficersServiceImpl implements OfficersService{
	
	@Autowired
	Officers officers;
	
	@Autowired
	Nominatores nominatores;
	
	@Autowired
	MyEmailSender emailSender;
	
	@Autowired
	OfficersRepository officersRepository;
	
	@Autowired
	VotersRepository votersRepository;
	
	@Autowired
	NominatoresRepository nominatoresRepository;
	
	@Autowired
	CloudinaryHelper cloudinaryHelper;

	@Override
	public String loadRegister(ModelMap map) {
		map.put("officers", officers);
		return "officer-register.html";
	}

	@Override
	public String loadRegister(@Valid Officers officers, BindingResult result, HttpSession session) {
		if (!officers.getPassword().equals(officers.getConfirmpassword()))
			result.rejectValue("confirmpassword", "error.confirmpassword", "* Password Missmatch");
		if (votersRepository.existsByEmail(officers.getEmail()) || officersRepository.existsByEmail(officers.getEmail()))
			result.rejectValue("email", "error.email", "* Email should be Unique");
		if (votersRepository.existsByMobile(officers.getMobile())
				|| officersRepository.existsByMobile(officers.getMobile()))
			result.rejectValue("mobile", "error.mobile", "* Mobile Number should be Unique");

		if (result.hasErrors())
			return "officer-register.html";
		else {
			int otp = new Random().nextInt(100000, 1000000);
			officers.setOtp(otp);
			officers.setPassword(AES.encrypt(officers.getPassword(), "123"));
			officersRepository.save(officers);
			emailSender.sendOtp(officers);

			session.setAttribute("success", "Otp Sent Success");
			session.setAttribute("id", officers.getId());
			return "redirect:/officer/otp";
		}
	}

	@Override
	public String submitOtp(int id, int otp, HttpSession session) {
		Officers office = officersRepository.findById(id).orElseThrow();
		if (office.getOtp() == otp) {
			office.setVerified(true);
			officersRepository.save(office);
			session.setAttribute("success", "Account Created Success");
			return "redirect:/";
		} else {
			session.setAttribute("failure", "Invalid OTP");
			session.setAttribute("id", office.getId());
			return "redirect:/officer/otp";
		}
	}

	@Override
	public String loadHome(HttpSession session) {
		if (session.getAttribute("officers") != null)
			return "officer-home.html";
		else {
			session.setAttribute("failure", "Invalid Session, Login Again");
			return "redirect:/login";
		}
	}

	@Override
	public String displayVoters(int voterid, HttpSession session, ModelMap map) {
		
			Voters voter=votersRepository.findByVoterid(voterid);
			
			if (voter == null) {
	            session.setAttribute("failure", "No voter found with the given ID.");
	            return "redirect:/officer/home";
	        } 
			
	            map.put("voters", voter); 
	            return "displayvoter.html"; 
	       	}

	@Override
	public String addNominatores(HttpSession session, ModelMap map) {
		if(session.getAttribute("officers")!=null) {
			map.put("nominatores", nominatores);
			return "add-nominatores.html";
		}else {
			session.setAttribute("failure","Invalid Session, Login Again");
			return "redirect:/login";
		}
	}

	@Override
	public String addNominatores(HttpSession session, @Valid Nominatores nominatores, BindingResult result,
			MultipartFile image, MultipartFile images) {
		if(session.getAttribute("officers")!=null) {
			if(result.hasErrors()) {
				return "add-nominatores.html";
			}
			else {
				nominatores.setOfficers((Officers) session.getAttribute("officers"));
				nominatores.setImageLink(cloudinaryHelper.saveImage(image));
				nominatores.setPartlogoLink(cloudinaryHelper.saveImage(images));
				nominatoresRepository.save(nominatores);
				session.setAttribute("success", "Nominatores Added Success");
				return "redirect:/officer/home";
				
				
			}
		}
		else {
			session.setAttribute("failure", "Invalid Session, Login Again");
			return "redirect:/login";
		}
	}

	@Override
	public String viewNominatores(HttpSession session, ModelMap map) {
	    if (session.getAttribute("officers") != null) {
	        Officers officer = (Officers) session.getAttribute("officers");
	        List<Nominatores> nominatores = nominatoresRepository.findByOfficers_Id(officer.getId());
	        if (nominatores == null || nominatores.isEmpty()) {
	            session.setAttribute("failure", "No Nominatores added Yet");
	            return "redirect:/officer/home";
	        } else {
	            map.put("nominatores", nominatores);
	            return "officer-nominatores.html";
	        }
	    } else {
	        session.setAttribute("failure", "Invalid Session, Login Again");
	        return "redirect:/login";
	        
	    }
	}

	@Override
	public String deleteNominatores(HttpSession session, int id) {
		if(session.getAttribute("officers")!=null) {
			nominatoresRepository.deleteById(id);
			session.setAttribute("success", "Nominatore Deleted Success");
			return "redirect:/officer/manage-nominatores";
		}else {
			session.setAttribute("failure", "Invalid Session,Login Again");
			return "redirect:/login";
		}
	}

	@Override
	public String editNominatores(HttpSession session, int id, ModelMap map) {
		if(session.getAttribute("officers")!= null) {
			Nominatores nominatores=nominatoresRepository.findById(id).orElseThrow();
			map.put("nominatores", nominatores);
			return "edit-nominatores.html";
		}
		else {
			session.setAttribute("failure","Invalid Session,Login Again");
			return "redirect:/login";
		}
	}

	@Override
	public String updateNominatores(HttpSession session, @Valid Nominatores nominatores, BindingResult result,
			MultipartFile image, MultipartFile images) {
		if(session.getAttribute("officers")!=null) {
			if(result.hasErrors()) {
				return "edit-nominatores.html";
			}
			else {
				nominatores.setOfficers((Officers) session.getAttribute("officers"));
				
				byte[] picture;
				byte[] pictures;
				
				try {
					picture = new byte[image.getInputStream().available()];
					image.getInputStream().read(picture);

					if(picture.length>0)
					nominatores.setImageLink(cloudinaryHelper.saveImage(image));
					else
					nominatores.setImageLink(nominatoresRepository.findById(nominatores.getId()).orElseThrow().getImageLink()); 
				
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				try {
					pictures = new byte[images.getInputStream().available()];
					images.getInputStream().read(pictures);

					if(pictures.length>0)
					nominatores.setPartlogoLink(cloudinaryHelper.saveImage(images));
					else
					nominatores.setPartlogoLink(nominatoresRepository.findById(nominatores.getId()).orElseThrow().getPartlogoLink()); 
				
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				officersRepository.save(officers);
				session.setAttribute("success", "Nominatores Updated Success");
				return "redirect:/officer/manage-nominatores";
			}
		}
		else {
			session.setAttribute("failure", "Invalid Session,Login Again");
			return "redirect:/login";
		}
	}

	@Override
	public String displayVoters(HttpSession session, int id) {
		if(session.getAttribute("officers")!=null) {
			Voters voters=votersRepository.findById(id).orElseThrow();
			voters.setVerified(true);
			votersRepository.save(voters);
			session.setAttribute("success", "voterId approved Successfully");
			return "redirect:/officer/home";
			
		}
		else {
			session.setAttribute("failure", "Invalid Session,Login Again");
			return "redirect:/login";
		}
	}

	@Override
	public String displayVoters(HttpSession session, ModelMap map, int id) {
		if(session.getAttribute("officers")!=null) {
			Voters voters=votersRepository.findById(id).orElseThrow();
			voters.setVerified(false);
			votersRepository.save(voters);
			session.setAttribute("success", "voterId rejected Successfully");
			return "redirect:/officer/home";
			
		}
		else {
			session.setAttribute("failure", "Invalid Session,Login Again");
			return "redirect:/login";
		}
	}

	@Override
	public String resendOtp(int id, HttpSession session) {
		Officers officers=officersRepository.findById(id).orElseThrow();
		int otp = new Random().nextInt(100000, 1000000);
		officers.setOtp(otp);
		officersRepository.save(officers);
		emailSender.sendOtp(officers);
		
		session.setAttribute("success", "Otp Resent Success");
		session.setAttribute("id", officers.getId());
		return "redirect:/officer/otp";
	}

}


