package br.com.etechas.clubelivro.service;

import br.com.etechas.clubelivro.dto.LivroResponseDTO;
import br.com.etechas.clubelivro.entity.Livro;
import br.com.etechas.clubelivro.mapper.LivroMapper;
import br.com.etechas.clubelivro.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private LivroMapper livroMapper;

    public List<LivroResponseDTO> listarTodos() {
        var result = livroRepository.findAll();
        return livroMapper.toResponseDTOList(result);
    }

    public Optional<LivroResponseDTO> buscarPorId(Integer id) {
        return livroRepository.findById(id).map(livroMapper::toResponseDTO);
    }

    public LivroResponseDTO cadastrar(LivroResponseDTO dto) {
        var livro = livroMapper.toEntity(dto);
        livro.setId(null);
        livro.setStatus("DISPONIVEL");
        livro.setDataEmprestimo(null);
        livro.setDataDevolucaoPrevista(null);

        var result = livroRepository.save(livro);
        return livroMapper.toResponseDTO(result);
    }

    public Optional<LivroResponseDTO> atualizar(Integer id, LivroResponseDTO dto) {
        return livroRepository.findById(id).map(livro -> {
            livro.setTitulo(dto.titulo());
            livro.setDono(dto.dono());

            return livroMapper.toResponseDTO(livroRepository.save(livro));
        });
    }

    public boolean excluir(Integer id) {
        if (!livroRepository.existsById(id)) {
            return false;
        }

        livroRepository.deleteById(id);
        return true;
    }

    public Optional<LivroResponseDTO> emprestarLivro(Integer id) {
        return livroRepository.findById(id).map(livro -> {
            if ("EMPRESTADO".equals(livro.getStatus())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Este livro já está emprestado no momento."
                );
            }

            LocalDate hoje = LocalDate.now();

            livro.setStatus("EMPRESTADO");
            livro.setDataEmprestimo(hoje);
            livro.setDataDevolucaoPrevista(hoje.plusDays(7));

            return livroMapper.toResponseDTO(livroRepository.save(livro));
        });
    }

    public Optional<LivroResponseDTO> devolverLivro(Integer id) {
        return livroRepository.findById(id).map(livro -> {
            livro.setStatus("DISPONIVEL");
            livro.setDataEmprestimo(null);
            livro.setDataDevolucaoPrevista(null);

            return livroMapper.toResponseDTO(livroRepository.save(livro));
        });
    }

}