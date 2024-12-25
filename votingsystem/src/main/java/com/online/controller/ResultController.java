package com.online.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.online.dto.Nominatores;
import com.online.repository.NominatoresRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/result")
public class ResultController {
	
	@Autowired
	NominatoresRepository nominatoresRepository;
	
	@GetMapping("/home")
	public String loadHome(HttpSession session,ModelMap map) {
		if (session.getAttribute("admin") != null) {
			List<Nominatores> nominator = nominatoresRepository.findAll();
			map.put("nominator", nominator);
			return "result-home.html";
		}
		else {
			session.setAttribute("failure", "Invalid Session, Login Again");
			return "redirect:/login";
		}
	}

}
