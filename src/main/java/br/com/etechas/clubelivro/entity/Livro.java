package br.com.etechas.clubelivro.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "livro")
@Getter
@Setter
@NoArgsConstructor
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "dono")
    private String dono;

    @Column(name = "status")
    private String status = "DISPONIVEL";

    @Column(name = "dataEmprestimo")
    private LocalDate dataEmprestimo;

    @Column(name = "dataDevolucaoPrevista")
    private LocalDate dataDevolucaoPrevista;
}
