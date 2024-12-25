package com.online.service.implementation;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.online.dto.Officers;
import com.online.dto.Voters;
import com.online.helper.AES;
import com.online.helper.MyEmailSender;
import com.online.repository.OfficersRepository;
import com.online.repository.VotersRepository;

import jakarta.servlet.http.HttpSession;



@Service
public class MainServiceImpl implements MainService{
	
	@Value("${admin.email}")
    private String adminEmail;

    @Autowired
    MyEmailSender emailSender;

    @Value("${admin.password}")
    private String adminPassword;
    
    @Autowired
    OfficersRepository officersRepository;
    
    @Autowired
    VotersRepository votersRepository;

	@Override
	public String loadHome(ModelMap map) {
		return "home.html";
	}

	@Override
	public String loadLogin() {
		return "login.html";
	}

	@Override
	public String login(String email, String password, HttpSession session) {
		
		if (email.equals(adminEmail) && password.equals(adminPassword)) {
            session.setAttribute("admin", "admin");
            session.setAttribute("success", "Login Success");
            return "redirect:/result/home";
        } else {
            Officers officer = officersRepository.findByEmail(email);
            

            if (officer == null) {
                session.setAttribute("failure", "Invalid Email");
                return "redirect:/login";
            } else {
            	
                    if (AES.decrypt(officer.getPassword(), "123").equals(password)) {
                        if (officer.isVerified()) {
                            session.setAttribute("officers", officer);
                            session.setAttribute("success", "Login Success");
                            return "redirect:/officer/home";
                        } else {
                            officer.setOtp(new Random().nextInt(100000, 1000000));
                            officersRepository.save(officer);
                            emailSender.sendOtp(officer);
                            session.setAttribute("success", "Otp Sent Success");
                            session.setAttribute("id", officer.getId());
                            return "redirect:/officer/otp";
                        }
                    } else {
                        session.setAttribute("failure", "Invalid Passowrd");
                        return "redirect:/login";
                    }
                }
            }
	}

	@Override
	public String logout(HttpSession session) {
		session.removeAttribute("admin");
		session.removeAttribute("officers");
		session.removeAttribute("voters");
		session.setAttribute("success", "Logged out Successfully");
		return "redirect:/"; 
	}
}
	

