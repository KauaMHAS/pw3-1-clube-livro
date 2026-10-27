package br.com.etechas.clubelivro.service;

import org.springframework.stereotype.Service;

@Service
public class LivroService {

    /*
    Função emprestarLivro(livroId):
    livro = repository.buscarPorId(livroId)

    Se livro.status == "EMPRESTADO" Então
        LançarErro("Este livro já está emprestado no momento.")
    FimSe

    livro.status = "EMPRESTADO"
    livro.dataEmprestimo = hoje()
    livro.dataDevolucaoPrevista = hoje() + 7 dias

    Retornar repository.salvar(livro)
    FimFuncao
     */



}
