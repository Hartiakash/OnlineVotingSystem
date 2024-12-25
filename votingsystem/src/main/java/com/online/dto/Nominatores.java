package com.online.dto;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity
@Data
@Component
public class Nominatores {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Size(min = 3, max = 15, message = "* Enter between 3~15 characters")
	private String name;
	@DecimalMin(value = "6000000000", message = "* Enter Proper Mobile Number")
	@DecimalMax(value = "9999999999", message = "* Enter Proper Mobile Number")
	@NotNull(message = "* Enter Proper Mobile Number")
	private long mobile;
	@Email(message = "* Enter Proper Email")
	@NotEmpty(message = "* Enter Proper Email")
	private String email;
	@Pattern(
		    regexp = "^(https?|ftp)://.*\\.(jpg|png)$",
		    message = "* Enter a valid image URL ending with .jpg or .png"
		)
	private String imageLink;
	@Pattern(
		    regexp = "^(https?|ftp)://.*\\.(jpg|png)$",
		    message = "* Enter a valid image URL ending with .jpg or .png"
		)
	private String partlogoLink;
	@Size(min = 6, max = 150, message = "* Enter between 6~150 characters")
	private String address;
	private int votes;
	
	@ManyToOne
	Officers officers;

}
