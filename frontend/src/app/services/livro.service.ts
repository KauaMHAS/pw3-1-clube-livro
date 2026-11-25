
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Livro } from '../models/livro';

@Injectable({
    providedIn: 'root'
})
export class LivroService {

    private apiUrl = 'http://localhost:8080/api/livros';

    constructor(private http: HttpClient) {}

    listarTodos(): Observable<Livro[]> {
        return this.http.get<Livro[]>(this.apiUrl);
    }

    buscarPorId(id: number): Observable<Livro> {
        return this.http.get<Livro>(`${this.apiUrl}/${id}`);
    }

    cadastrar(livro: Livro): Observable<Livro> {
        return this.http.post<Livro>(this.apiUrl, livro);
    }

    atualizar(id: number, livro: Livro): Observable<Livro> {
        return this.http.put<Livro>(`${this.apiUrl}/${id}`, livro);
    }

    excluir(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }

    emprestarLivro(id: number): Observable<Livro> {
        return this.http.post<Livro>(`${this.apiUrl}/${id}/emprestar`, null).pipe(
            catchError((erro: HttpErrorResponse) => {
                if (erro.status === 409) {
                    alert('Este livro já está emprestado.');
                } else if (erro.status === 400) {
                    alert('Não foi possível realizar o empréstimo. Verifique as informações.');
                } else {
                    alert('Não foi possível emprestar o livro.');
                }

                return throwError(() => erro);
            })
        );
    }

    devolverLivro(id: number): Observable<Livro> {
        return this.http.post<Livro>(`${this.apiUrl}/${id}/devolver`, null);
    }

}
