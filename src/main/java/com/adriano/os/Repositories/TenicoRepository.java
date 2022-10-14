package com.adriano.os.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adriano.os.domain.Tecnico;

@Repository
public interface TenicoRepository extends JpaRepository<Tecnico, Integer> {

	@Query("SELECT obj FROM TB_Tecnico obj WHERE obj.cpf =:cpf")
	Tecnico findByCPF(@Param("cpf") String cpf);
	
}
