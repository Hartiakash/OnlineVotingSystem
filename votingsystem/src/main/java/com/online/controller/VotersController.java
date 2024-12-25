package com.online.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.online.dto.Nominatores;
import com.online.dto.Voters;
import com.online.repository.NominatoresRepository;
import com.online.repository.VotersRepository;
import com.online.service.implementation.VotersService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/voters")
public class VotersController {
	
	@Autowired
	VotersService votersService;
	
	@Autowired
	VotersRepository votersRepository;
	
	@GetMapping("/register")
	public String loadRegister(ModelMap map) {
		return votersService.loadRegister(map);
	}
	
	@PostMapping("/register")
	public String register(@Valid Voters voters, BindingResult result, HttpSession session,@RequestParam MultipartFile image) {
		return votersService.loadRegister(voters, result, session,image);
	}
	
	@GetMapping("/verified")
	public String displayVoters() {
		return "verified-voter.html";
	}
	
	@GetMapping("/verified/search")	
	public String displayVoters(@RequestParam("voterid") int voterid,ModelMap map,HttpSession session) {
		return votersService.displayVoters(voterid,map,session);
	}
	
	
	@GetMapping("/home")
	public String loadHome(HttpSession session) {
		return votersService.loadHome(session);
	}
	
	
	@GetMapping("/vote/{id}/{voterid}")
    public String vote(@PathVariable int id, @PathVariable int voterid, HttpSession session) {
        return votersService.vote(id,voterid,session);
    }
	
	
	
	

	
	
	
	

}
