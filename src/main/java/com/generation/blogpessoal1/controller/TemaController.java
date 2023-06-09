package com.generation.blogpessoal1.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal1.model.Tema;
import com.generation.blogpessoal1.repository.TemaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {
	@Autowired
	private TemaRepository temaRepository;
	
	@GetMapping
	public ResponseEntity<List<Tema>> getAll(){
		// SELECT * FROM tb_temas
		return ResponseEntity.ok(temaRepository.findAll());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Tema> getById(@PathVariable Long id){
		// SELECT * FROM tb_temas WHERE id = ?
		return temaRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Tema>> getByDescricao(@PathVariable String descricao){
		// SELECT * FROM tb_temas WHERE descricao LIKE "%???%"
		return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	@PostMapping
	public ResponseEntity<Tema> post(@Valid @RequestBody Tema tema){
		// INSERT INTO tb_temas(descricao) VALUES ("???")
		return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(tema));
	}
	
	@PutMapping
	public ResponseEntity<Tema> put(@Valid @RequestBody Tema tema){
		// UPDATE tb_temas SET descricao = "???" WHERE id = ?
		
		return temaRepository.findById(tema.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(temaRepository.save(tema)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		// DELETE FROM tb_temas WHERE id = ?
		
		Optional<Tema> optional = temaRepository.findById(id);
		
		if (optional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		else {
			temaRepository.deleteById(id);
		}
	}
}