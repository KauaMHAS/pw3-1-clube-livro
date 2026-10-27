package br.com.etechas.clubelivro.mapper;

import br.com.etechas.clubelivro.dto.LivroReponseDTO;
import br.com.etechas.clubelivro.entity.Livro;

import java.util.List;

public interface LivroMapper {

    List<LivroReponseDTO> toRespostaDTOList(List<Livro> entities);

    LivroReponseDTO toResponseDTO(Livro entity);

    Livro toEntity(LivroReponseDTO dto);
}
