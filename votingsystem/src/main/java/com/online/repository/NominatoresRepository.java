package com.online.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.online.dto.Nominatores;

import jakarta.transaction.Transactional;

public interface NominatoresRepository extends JpaRepository<Nominatores, Integer>{


	List<Nominatores> findByOfficers_Id(int id);
	
	
}
