import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Livro } from '../models/livro';
import { LivroService } from '../services/livro.service';

@Component({
    selector: 'app-livros-lista',
    standalone: true,
    imports: [RouterLink],
    templateUrl: './livros-lista.html',
    styleUrl: './livros-lista.css'
})
export class LivrosListaComponent implements OnInit {

    livros: Livro[] = [];

    constructor(
        private livroService: LivroService,
        private cdr: ChangeDetectorRef
    ) {}

    ngOnInit(): void {
        this.carregarLivros();
    }

    carregarLivros(): void {
        this.livroService.listarTodos().subscribe({
            next: (dados) => {
                this.livros = dados;
                this.cdr.markForCheck();
            },
            error: () => {
                alert('Não foi possível carregar os livros.');
            }
        });
    }

    emprestarLivro(id: number | null): void {
        if (id === null) {
            return;
        }

        this.livroService.emprestarLivro(id).subscribe({
            next: () => {
                alert('Livro emprestado com sucesso!');
                this.carregarLivros();
            },
            error: (erro) => {
                if (erro.status === 409) {
                    alert('Este livro já está emprestado.');
                } else {
                    alert('Não foi possível emprestar o livro.');
                }
            }
        });
    }

    devolverLivro(id: number | null): void {
        if (id === null) {
            return;
        }

        this.livroService.devolverLivro(id).subscribe({
            next: () => {
                alert('Livro devolvido com sucesso!');
                this.carregarLivros();
            },
            error: () => {
                alert('Não foi possível devolver o livro.');
            }
        });
    }

    excluirLivro(id: number | null, titulo: string): void {
        if (id === null) {
            return;
        }

        if (confirm(`Deseja realmente excluir o livro "${titulo}"?`)) {
            this.livroService.excluir(id).subscribe({
                next: () => {
                    alert('Livro excluído com sucesso!');
                    this.carregarLivros();
                },
                error: () => {
                    alert('Não foi possível excluir o livro.');
                }
            });
        }
    }

}
