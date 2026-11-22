import { Routes } from '@angular/router';
import { LivrosListaComponent } from './livros-lista/livros-lista';
import { LivroFormularioComponent } from './formulario/formulario';

export const routes: Routes = [
    { path: 'livros/novo', component: LivroFormularioComponent },
    { path: 'livros/editar/:id', component: LivroFormularioComponent },
    { path: '', component: LivrosListaComponent }
];
