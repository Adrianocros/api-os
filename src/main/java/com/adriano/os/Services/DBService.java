package com.adriano.os.Services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adriano.os.Repositories.ClienteRepository;
import com.adriano.os.Repositories.OSRepository;
import com.adriano.os.Repositories.TenicoRepository;
import com.adriano.os.domain.Cliente;
import com.adriano.os.domain.OS;
import com.adriano.os.domain.Tecnico;
import com.adriano.os.domain.enuns.Prioridade;
import com.adriano.os.domain.enuns.Status;

@Service
public class DBService {

	@Autowired
	private TenicoRepository tenicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private OSRepository osRepository;
	
	
	public void instanciaDB() {
		Tecnico t1 = new Tecnico(null, "Adriano Crosoletto", "475.111.410-72", "119292992");
		Tecnico t2 = new Tecnico(null, "Ricardo", "809.490.820-31", "(11) 993929929");
		
		Cliente c1 = new Cliente(null,"Betina Campos","229.399.780-42", "1198939392");
		Cliente c2 = new Cliente(null,"Maria Regina","456.981.100-00","(21) 83828881" );
		
		OS os1 = new OS(null,Prioridade.ALTA ,"Teste de criação de OS", Status.ABERTO, t1, c1);
		OS os2 = new OS(null, Prioridade.MEDIA, "Falha ao iniciar", Status.ANDAMENTO, t2, c2);
		
		t1.getList().add(os1);
		c1.getList().add(os1);
		t2.getList().add(os2);
		c1.getList().add(os2);
		
		tenicoRepository.saveAll(Arrays.asList(t1,t2));
		clienteRepository.saveAll(Arrays.asList(c1,c2));
		osRepository.saveAll(Arrays.asList(os1,os2));
	}
}
