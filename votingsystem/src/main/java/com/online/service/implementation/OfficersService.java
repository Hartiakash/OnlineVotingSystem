package com.online.service.implementation;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.online.dto.Nominatores;
import com.online.dto.Officers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

public interface OfficersService {

	String loadRegister(ModelMap map);

	String loadRegister(@Valid Officers officers, BindingResult result, HttpSession session);

	String submitOtp(int id, int otp, HttpSession session);

	String loadHome(HttpSession session);

	String displayVoters(int voterid, HttpSession session, ModelMap map);

	String addNominatores(HttpSession session, ModelMap map);

	String addNominatores(HttpSession session, @Valid Nominatores nominatores, BindingResult result,
			MultipartFile image, MultipartFile images);

	String viewNominatores(HttpSession session, ModelMap map);

	String deleteNominatores(HttpSession session, int id);

	String editNominatores(HttpSession session, int id, ModelMap map);

	String updateNominatores(HttpSession session, @Valid Nominatores nominatores, BindingResult result,
			MultipartFile image, MultipartFile images);

	String displayVoters(HttpSession session, int id);

	String displayVoters(HttpSession session, ModelMap map, int id);

	String resendOtp(int id, HttpSession session);

	

}
