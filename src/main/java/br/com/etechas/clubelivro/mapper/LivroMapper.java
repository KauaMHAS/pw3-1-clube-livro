package br.com.etechas.clubelivro.mapper;

import br.com.etechas.clubelivro.dto.LivroResponseDTO;
import br.com.etechas.clubelivro.entity.Livro;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LivroMapper {

    List<LivroResponseDTO> toResponseDTOList(List<Livro> entities);

    LivroResponseDTO toResponseDTO(Livro entity);

    Livro toEntity(LivroResponseDTO dto);

}