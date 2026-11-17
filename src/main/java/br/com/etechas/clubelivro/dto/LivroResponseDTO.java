package br.com.etechas.clubelivro.dto;

import java.time.LocalDate;

public record LivroResponseDTO(
        Integer id,
        String titulo,
        String dono,
        String status,
        LocalDate dataEmprestimo,
        LocalDate dataDevolucaoPrevista
) {
}