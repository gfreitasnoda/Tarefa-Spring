package br.com.tarefa.projeto.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tarefa.projeto.domain.Estado;
import br.com.tarefa.projeto.domain.Tarefas;
import br.com.tarefa.projeto.repository.TarefasRepository;

@RestController
public class TarefasController {
		
		@Autowired
		private TarefasRepository tr;
		
		@GetMapping("/tarefa/listar")
		public List<Tarefas> listar(){
			return tr.findAll();
		}
		
		@GetMapping("/tarefa/listar/{titulo}")
		public List<Tarefas> listarT(@PathVariable String titulo){
			return tr.findByTitulo(titulo);
		}
		
		@GetMapping("/tarefa/listar/Aberta")
		public List<Tarefas> findByEstado(Estado estado) {
		    return tr.findByEstado(estado.Aberta);
		}
		
		@GetMapping("/tarefa/listar/Finalizada")
		public List<Tarefas> findByEstadoo(Estado estado) {
		    return tr.findByEstado(estado.Finalizada);
		}


		
		@PostMapping("/tarefa/cadastrar")
		public String cadastrar(@RequestBody Tarefas tarefas) {
			
			tr.save(tarefas);
			return "Tarefa cadastrada";
			
		}
		
		@PutMapping("/tarefa/atualizar/{id}")
		public String atualizar(@PathVariable Integer id, @RequestBody Tarefas tarefas) {
			
			String msg = "";
			Optional<Tarefas> t = tr.findById(id);
			if(t.isPresent()) {
				tarefas.setIdtarefa(id);
				tr.save(tarefas);
				msg = "Tarefa atualizada";
			}
			else {
				msg = "Tarefa não encontrada";
			}
			return msg;
		}

		@DeleteMapping("/tarefa/apagar/{id}")
		public String apagar (@PathVariable Integer id) {
			String msg = "";
			Optional<Tarefas> t = tr.findById(id);
			if(t.isPresent()) {
				tr.deleteById(id);
				msg = " Tarefa apagada";
			}
			else {
				msg = "Tarefa não encontrada";
			}
			return msg;
		}
}
		
		
		
	

