export interface Livro {
    id: number | null;
    titulo: string;
    dono: string;
    status: string;
    dataEmprestimo: string | null;
    dataDevolucaoPrevista: string | null;
}
