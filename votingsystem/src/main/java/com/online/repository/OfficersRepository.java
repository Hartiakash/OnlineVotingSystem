package com.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.dto.Officers;

public interface OfficersRepository extends JpaRepository<Officers, Integer>{

	boolean existsByEmail(String email);

	boolean existsByMobile(long mobile);

	Officers findByEmail(String email);

}
