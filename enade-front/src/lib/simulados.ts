export type SimuladoStatus = 'concluido' | 'pendente';

export type Simulado = {
    id: string;
    titulo: string;
    tema: string;
    quantidadeQuestoes: number;
    data: string;
    status: SimuladoStatus;
    tempo: number;
};

export type Questao = {
    id: string;
    simuladoId: string;
    pergunta: string;
    alternativas: string[];
    correta: number;
};
