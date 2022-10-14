package com.adriano.os.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adriano.os.Repositories.OSRepository;
import com.adriano.os.Services.exceptions.ObjectNotFoundException;
import com.adriano.os.domain.Cliente;
import com.adriano.os.domain.OS;
import com.adriano.os.domain.Tecnico;
import com.adriano.os.domain.enuns.Prioridade;
import com.adriano.os.domain.enuns.Status;
import com.adriano.os.dtos.OSDTO;


@Service
public class osService {
	@Autowired
	private OSRepository osReposiotry;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	
	@Autowired
	private ClienteService clienteService;
	
	public OS findById(Integer id ) {
		Optional<OS> obj = osReposiotry.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("OS não encontrada! Id" + id ));
	}
	
	public List<OS>findAll(){
		return osReposiotry.findAll();
	}

	public OS create(@Valid OSDTO obj) {
		return fromDTO(obj);
		
	}
	
	public OS update(@Valid OSDTO obj) {
		findById(obj.getId());
		return fromDTO(obj);
	}
	
	
	private OS fromDTO(OSDTO obj) {
		OS newObj = new OS();
		newObj.setId(obj.getId());
		newObj.setObservacoes(obj.getObservacoes());
		newObj.setPrioridade(obj.getPrioridade());
		newObj.setStatus(Status.toEnum(obj.getStatus().getCod()));
				
		Tecnico tec = tecnicoService.findById(obj.getTecnico());
		Cliente cli = clienteService.findById(obj.getCliente());
		
		newObj.setTecnico(tec);
		newObj.setCliente(cli);
		
		//Caso fechado inclui a data fechamamento na OS
		if(newObj.getStatus().getCod().equals(2)) {
			newObj.setDataFechamento(LocalDateTime.now());
		}
		return osReposiotry.save(newObj);
	}
}
