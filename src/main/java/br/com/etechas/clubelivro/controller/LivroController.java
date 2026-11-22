package br.com.etechas.clubelivro.controller;

import br.com.etechas.clubelivro.dto.LivroResponseDTO;
import br.com.etechas.clubelivro.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
@CrossOrigin(origins = "http://localhost:4200")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public List<LivroResponseDTO> listarTodos() {
        return livroService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> buscarPorId(@PathVariable Integer id) {
        var result = livroService.buscarPorId(id);

        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> cadastrar(@RequestBody LivroResponseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.cadastrar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> atualizar(
            @PathVariable Integer id,
            @RequestBody LivroResponseDTO dto) {
        var result = livroService.atualizar(id, dto);

        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        if (livroService.excluir(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/emprestar")
    public ResponseEntity<LivroResponseDTO> emprestar(@PathVariable Integer id) {
        var result = livroService.emprestarLivro(id);

        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/devolver")
    public ResponseEntity<LivroResponseDTO> devolver(@PathVariable Integer id) {
        var result = livroService.devolverLivro(id);

        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}