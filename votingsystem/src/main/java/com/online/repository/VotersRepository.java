package com.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.online.dto.Voters;


public interface VotersRepository extends JpaRepository<Voters, Integer>{

	boolean existsByEmail(String email);

	boolean existsByMobile(long mobile);

	Voters findByVoterid(int voterid);

	Voters findByEmail(String email);

	

	
	

	

}
