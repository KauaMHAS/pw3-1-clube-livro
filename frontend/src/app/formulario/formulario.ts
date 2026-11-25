
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Livro } from '../models/livro';
import { LivroService } from '../services/livro.service';

@Component({
    selector: 'app-livro-formulario',
    standalone: true,
    imports: [ReactiveFormsModule],
    templateUrl: './formulario.html',
    styleUrl: './formulario.css'
})
export class LivroFormularioComponent implements OnInit {

    formulario!: FormGroup<{
        titulo: FormControl<string>;
        dono: FormControl<string>;
    }>;

    modoEdicao = false;
    idLivro: number | null = null;
    livroAtual: Livro | null = null;

    constructor(
        private formBuilder: FormBuilder,
        private livroService: LivroService,
        private route: ActivatedRoute,
        private router: Router
    ) {
        this.formulario = this.formBuilder.nonNullable.group({
            titulo: ['', [
                Validators.required,
                Validators.minLength(2),
                Validators.maxLength(150),
                Validators.pattern(/\S/)
            ]],
            dono: ['', [
                Validators.required,
                Validators.minLength(2),
                Validators.maxLength(150),
                Validators.pattern(/\S/)
            ]]
        });
    }

    ngOnInit(): void {
        const id = this.route.snapshot.paramMap.get('id');

        if (id !== null) {
            this.modoEdicao = true;
            this.idLivro = Number(id);
            this.carregarLivro();
        }
    }

    carregarLivro(): void {
        if (this.idLivro === null) {
            return;
        }

        this.livroService.buscarPorId(this.idLivro).subscribe({
            next: (livro) => {
                this.livroAtual = livro;
                this.formulario.patchValue({
                    titulo: livro.titulo,
                    dono: livro.dono
                });
            },
            error: () => {
                alert('Não foi possível carregar os dados do livro.');
                this.router.navigate(['/']);
            }
        });
    }

    salvar(): void {
        if (this.formulario.invalid) {
            this.formulario.markAllAsTouched();
            return;
        }

        const dados = this.formulario.getRawValue();

        const livro: Livro = {
            id: this.idLivro,
            titulo: dados.titulo.trim(),
            dono: dados.dono.trim(),
            status: this.livroAtual?.status ?? 'DISPONIVEL',
            dataEmprestimo: this.livroAtual?.dataEmprestimo ?? null,
            dataDevolucaoPrevista: this.livroAtual?.dataDevolucaoPrevista ?? null
        };

        if (this.modoEdicao && this.idLivro !== null) {
            this.livroService.atualizar(this.idLivro, livro).subscribe({
                next: () => {
                    alert('Livro atualizado com sucesso!');
                    this.router.navigate(['/']);
                },
                error: () => {
                    alert('Não foi possível atualizar o livro.');
                }
            });
        } else {
            this.livroService.cadastrar(livro).subscribe({
                next: () => {
                    alert('Livro cadastrado com sucesso!');
                    this.router.navigate(['/']);
                },
                error: () => {
                    alert('Não foi possível cadastrar o livro.');
                }
            });
        }
    }

    cancelar(): void {
        this.router.navigate(['/']);
    }

}
