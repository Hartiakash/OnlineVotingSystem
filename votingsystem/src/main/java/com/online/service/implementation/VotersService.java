package com.online.service.implementation;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.online.dto.Voters;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

public interface VotersService {

	String loadRegister(ModelMap map);

	String loadRegister(@Valid Voters voters, BindingResult result, HttpSession session, MultipartFile image);


	

	String loadHome(HttpSession session);

	String displayVoters(int voterid, ModelMap map,HttpSession session);

	String vote(int id, int voterid, HttpSession session);

	
	

	


	

}
