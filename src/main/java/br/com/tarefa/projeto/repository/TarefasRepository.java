package br.com.tarefa.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tarefa.projeto.domain.Estado;
import br.com.tarefa.projeto.domain.Tarefas;

public interface TarefasRepository extends JpaRepository <Tarefas, Integer> {
	
		List<Tarefas> findAll();
		List<Tarefas> findByTitulo(String titulo);
		 
		List<Tarefas> findByEstado(Estado estado);

}
