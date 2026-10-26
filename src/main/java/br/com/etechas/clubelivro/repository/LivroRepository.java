package br.com.etechas.clubelivro.repository;

import br.com.etechas.clubelivro.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository
        extends JpaRepository<Livro, Integer> {
}