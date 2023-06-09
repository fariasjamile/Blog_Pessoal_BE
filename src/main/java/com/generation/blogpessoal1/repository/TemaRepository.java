package com.generation.blogpessoal1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.blogpessoal1.model.Tema;


public interface TemaRepository extends JpaRepository<Tema, Long> {
	List<Tema> findAllByDescricaoContainingIgnoreCase(@Param("descricao")String descricao);

}
