package com.online.service.implementation;



import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.online.dto.Nominatores;
import com.online.dto.Voters;
import com.online.helper.AES;
import com.online.helper.CloudinaryHelper;
import com.online.helper.MyEmailSender;
import com.online.repository.NominatoresRepository;
import com.online.repository.VotersRepository;

import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Service
public class VotersServiceImpl implements VotersService{
	
	@Autowired
	MyEmailSender emailSender;
	
	@Autowired
	Voters voters;
	
	@Autowired
	Nominatores nominatores;
	
	@Autowired
	NominatoresRepository nominatoresRepository;
	
	@Autowired
	VotersRepository votersRepository;
	
	@Autowired
	CloudinaryHelper cloudinaryHelper;

	@Override
	public String loadRegister(ModelMap map) {
		map.put("voters", voters);
		return "voters-register.html";
	}

	@Override
	public String loadRegister(@Valid Voters voters, BindingResult result, HttpSession session, MultipartFile image) {
		if (result.hasErrors())
			return "voters-register.html";
		else {
			int voterid = new Random().nextInt(100000, 1000000);
			voters.setVoterid(voterid);
			voters.setImageLink(cloudinaryHelper.saveImage(image));
			votersRepository.save(voters);
			emailSender.sendOtp(voters);

			session.setAttribute("success", "VoterID  Sent to your Email");
			session.setAttribute("id", voters.getId());
			return "home.html";
		}
	}
	
	
		   
	@Override
	public String displayVoters(int voterid, ModelMap map,HttpSession session) {
	    // Retrieve voter details directly from the database
	    Voters voter = votersRepository.findByVoterid(voterid);
	    if (voter == null ) {
	        // If the voter is not found, redirect to the login page
	        session.setAttribute("failure", "VoterId is not Register");
	        return "redirect:/";
	    }

	    // Check if the voter is verified
	    if (!voter.isVerified()) {
	    	 session.setAttribute("failure", "VoterId is not verified By officer");
	        return "redirect:/voters/verified";
	    }

	    // Check if the voter has already voted
	    if (voter.isHasVoted()) {
	    	 session.setAttribute("failure", "you are already voted");
	        return "redirect:/voters/verified";
	    }

	    // Retrieve the list of nominators from the database
	    List<Nominatores> nominatores = nominatoresRepository.findAll();
	    if (nominatores == null || nominatores.isEmpty()) {
	    	 session.setAttribute("failure", "no Nominators");
	        return "redirect:/";
	    }

	    // Populate the ModelMap with voter details and the list of nominators
	    map.put("voter", voter); // Optional: add voter details to the map if needed
	    map.put("nominatores", nominatores);

	    // Return the view for displaying nominators
	    return "voter-nominatores.html";
	}

		


	@Override
	public String loadHome(HttpSession session) {
		if(session.getAttribute("voters")!=null)
			return "voter-home.html";
		else {
			session.setAttribute("failure", "Invalid session");
			return "redirect:/";
		}
	}

	@Override
	public String vote(int id, int voterid, HttpSession session) {
		Nominatores nominator = nominatoresRepository.findById(id).orElse(null);
        Voters voter = votersRepository.findByVoterid(voterid);

        if (nominator != null && voter != null && !voter.isHasVoted()) {
            nominator.setVotes(nominator.getVotes() + 1);
            nominatoresRepository.save(nominator);
            voter.setHasVoted(true);
            votersRepository.save(voter);

            session.setAttribute("success", "Vote is successful!");
        } else if (voter != null && voter.isHasVoted()) {
            session.setAttribute("failure", "You have already voted!");
        } else {
            session.setAttribute("failure", "Unable to process the vote.");
        }

        return "redirect:/"; // Adjust this if necessary
	}

	

	
	

	
	

}
