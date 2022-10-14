package com.adriano.os.Services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.adriano.os.Repositories.ClienteRepository;
import com.adriano.os.Repositories.PessoaRepository;
import com.adriano.os.Services.exceptions.DataIntegratyViolationException;
import com.adriano.os.Services.exceptions.ObjectNotFoundException;
import com.adriano.os.domain.Cliente;
import com.adriano.os.domain.Pessoa;
import com.adriano.os.dtos.ClienteDTO;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;


	public Cliente findById(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente, Id: " + id + " ,não encontado " ));
	}
	
	public List<Cliente> findAll(){
		return clienteRepository.findAll();
	}
		
	public Cliente create(ClienteDTO objDTO) {
		if(findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF ja cadastado na base de dados !");
		}
		return clienteRepository.save(new Cliente(null, objDTO.getNome(),objDTO.getCpf(), objDTO.getTelefone()));
	}
	
	/*Verifica se existe o cliente com ID informado*/
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		Cliente oldObj = findById(id);
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF ja cadastado na base de dados !");
		}
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return clienteRepository.save(oldObj);
	}

	
		public void delete(Integer id){
			Cliente obj = findById(id);
		
			if(obj.getList().size() > 0) {
				throw new DataIntegratyViolationException("Não é possiovel deleter o ID " + obj.getId() + " ,Cliente: " + obj.getNome() + " pois ha " +obj.getList().size()+ " Ordens de Serviços relacionadas a ele !");
			}
			clienteRepository.deleteById(id);
	 }
		/*Busca cliente por CPF*/
		private Pessoa findByCPF(ClienteDTO objDTO) {
			Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
			if(obj != null) {
				return obj;
			}
			return null;
		}
		
		
}
