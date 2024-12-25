package com.online.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.online.dto.Nominatores;
import com.online.repository.NominatoresRepository;
import com.online.service.implementation.MainService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@Autowired
	NominatoresRepository nominatoresRepository;
	
	@Autowired
	MainService mainService;
	
	@GetMapping("/")
	public String loadHome(ModelMap map) {
		return mainService.loadHome(map);
	}
	
	@GetMapping("/login")
	public String loadLogin() {
		return mainService.loadLogin();
			
		}
	@PostMapping("/login")
	public String login(@RequestParam String email,@RequestParam String password,HttpSession session) {
		return mainService.login(email,password,session);
	}
	
	@GetMapping("/logout")
    public String logout(HttpSession session) {
        return mainService.logout(session);
    }
	

}
