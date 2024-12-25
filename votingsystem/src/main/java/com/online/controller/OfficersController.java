package com.online.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.online.dto.Nominatores;
import com.online.dto.Officers;
import com.online.dto.Voters;
import com.online.repository.NominatoresRepository;
import com.online.repository.VotersRepository;
import com.online.service.implementation.OfficersService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/officer")
public class OfficersController {
	
	
	@Autowired
	VotersRepository votersRepository;
	
	@Autowired
	OfficersService officersService;
	
	@GetMapping("/register")
	public String loadRegister(ModelMap map) {
		return officersService.loadRegister(map);
	}
	
	@PostMapping("/register")
	public String register(@Valid Officers officers,BindingResult result,HttpSession session) {
		return officersService.loadRegister(officers,result,session);
	}
	
	@GetMapping("/otp")
	public String loadOtpPage() {
		return "officer-otp.html";
	}
	
	@PostMapping("/submit-otp/{id}")
	public String submitOtp(@PathVariable int id,@RequestParam int otp,HttpSession session) {
		return officersService.submitOtp(id,otp,session);
	}
	
	@GetMapping("/home")
	public String loadHome(HttpSession session) {
		return officersService.loadHome(session);
	}
	
	@GetMapping("/voters")
	public String displayVoters() {
		return "officer-voter.html";
	}
	
	@GetMapping("/voters/search")	
	public String displayVoters(@RequestParam("voterid") int voterid,HttpSession session,ModelMap map) {
		return officersService.displayVoters(voterid,session,map);
	}
	
	@GetMapping("/add-nominatores")
	public String addNominatores(HttpSession session,ModelMap map) {
		return officersService.addNominatores(session,map);
	}
	
	@PostMapping("/add-nominatores")
	public String addNominatores(HttpSession session,@Valid Nominatores nominatores,BindingResult result,@RequestParam MultipartFile image,@RequestParam MultipartFile images) {
		return officersService.addNominatores(session, nominatores,result,image,images);
	}
	
	@GetMapping("/manage-nominatores")
	public String viewNominatores(HttpSession session,ModelMap map) {
		return officersService.viewNominatores(session,map);
	}
	
	@GetMapping("/delete-nominatores/{id}")
	public String deleteNominatores(HttpSession session,@PathVariable int id) {
		return officersService.deleteNominatores(session,id);
	}
	
	@GetMapping("/edit-nominatores/{id}")
	public String editNominatores(HttpSession session,@PathVariable int id,ModelMap map) {
		return officersService.editNominatores(session,id,map);
	}
	
	@PostMapping("/edit-nominatores")
	public String updateNominatores(HttpSession session,@Valid Nominatores nominatores,BindingResult result,@RequestParam MultipartFile image,@RequestParam MultipartFile images) {
		return officersService.updateNominatores(session,nominatores,result,image,images);
	}
	
	@GetMapping("/approve-voters/{id}")
	public String displayVoters(HttpSession session,@PathVariable int id) {
		return officersService.displayVoters(session,id);
	}
	
	@GetMapping("/reject-voters/{id}")
	public String displayVoters(HttpSession session,ModelMap map,@PathVariable int id) {
		return officersService.displayVoters(session,map,id);
	}
	
	@GetMapping("/resend-otp/{id}")
	public String resendOtp(@PathVariable int id,HttpSession session) {
		return officersService.resendOtp(id,session);
	}
	
	

}
