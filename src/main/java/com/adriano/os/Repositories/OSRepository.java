package com.adriano.os.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adriano.os.domain.OS;

@Repository
public interface OSRepository extends JpaRepository<OS, Integer> {
	
}
