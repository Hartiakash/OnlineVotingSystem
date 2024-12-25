package com.online.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.online.dto.Officers;
import com.online.dto.Voters;

import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;


@Service
public class MyEmailSender {
	@Autowired
	JavaMailSender mailSender;

	@Autowired
	TemplateEngine templateEngine;

	public void sendOtp(Voters voters) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom("hartiakash05@gmail.com", "Online Voting System");
			helper.setTo(voters.getEmail());
			helper.setSubject("VoterID for Registration");

			Context context = new Context();
			context.setVariable("voters", voters);

			String text = templateEngine.process("voters-email.html", context);
			helper.setText(text, true);
			
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("**************" + voters.getVoterid() + "***********************");
	}

	public void sendOtp(@Valid Officers officers) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom("hartiakash05@gmail.com", "Online Voting System Site");
			helper.setTo(officers.getEmail());
			helper.setSubject("Otp for Account Creation");

			Context context = new Context();
			context.setVariable("officers", officers);

			String text = templateEngine.process("officer-email.html", context);
			helper.setText(text, true);
			
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("**************" + officers.getOtp() + "***********************");
	}

}
