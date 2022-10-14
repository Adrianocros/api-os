package com.adriano.os.Services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adriano.os.Repositories.PessoaRepository;
import com.adriano.os.Repositories.TenicoRepository;
import com.adriano.os.Services.exceptions.DataIntegratyViolationException;
import com.adriano.os.Services.exceptions.ObjectNotFoundException;
import com.adriano.os.domain.Pessoa;
import com.adriano.os.domain.Tecnico;
import com.adriano.os.dtos.TecnicoDTO;


@Service
public class TecnicoService {

	@Autowired
	private  TenicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado, Id: " + id + ", Tipo: " + Tecnico.class.getName()));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}
	
	public Tecnico create(TecnicoDTO objDTO) {
		if(findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF ja cadastado na base de dados !");
		}
		
		return repository.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(),objDTO.getTelefone()));
		
	}
	//Verifica se existe o tecnico com ID informado
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		Tecnico oldObj = findById(id);
		
		//Valida o CPF ja cadastrado
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF ja cadastado na base de dados !");
		}
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}
	/*Deleta o tecnico por ID*/
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Não é possiovel deleter o ID " + obj.getId() + " ,Tecnico: " + obj.getNome() + " pois ha " +obj.getList().size()+ " Ordens de Serviços relacionadas a ele !");
		}
		repository.deleteById(id);
	}
	
	//Busca tecnico por CPF
	private Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa  obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if(obj != null) {
			return obj;
		}
		return null;
	}

}
