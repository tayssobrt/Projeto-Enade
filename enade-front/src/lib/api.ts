import type { Simulado, SimuladoStatus } from '@/lib/simulados';

export type SimuladoSummary = {
    id: string;
    titulo: string;
    tema: string;
    quantidadeQuestoes: number;
    data: string;
    status: SimuladoStatus;
    tempo: number;
    lastAttempt: {
        score: number;
        finishedAt: string | null;
    } | null;
};

export type SimuladoDetail = {
    id: string;
    titulo: string;
    tema: string;
    descricao?: string;
    quantidadeQuestoes: number;
    data: string;
    tempo: number;
    status: SimuladoStatus;
    lastAttempt: {
        score: number;
        finishedAt: string | null;
    } | null;
};

export type QuestaoForClient = {
    id: string;
    pergunta: string;
    alternativas: string[];
};

export type SubmissionResult = {
    attemptId: string;
    score: number;
    total: number;
    correct: number;
};

const jsonHeaders = {
    'Content-Type': 'application/json',
};

function getApiBaseUrl() {
    if (typeof window !== 'undefined') {
        return '';
    }

    if (process.env.NEXT_PUBLIC_APP_URL) {
        return process.env.NEXT_PUBLIC_APP_URL.replace(/\/$/, '');
    }

    if (process.env.NEXTAUTH_URL) {
        return process.env.NEXTAUTH_URL.replace(/\/$/, '');
    }

    if (process.env.VERCEL_URL) {
        return `https://${process.env.VERCEL_URL.replace(/\/$/, '')}`;
    }

    return 'http://localhost:3000';
}

export async function fetchSimulados(query = ''): Promise<SimuladoSummary[]> {
    const baseUrl = getApiBaseUrl();
    const response = await fetch(`${baseUrl}/api/simulados?query=${encodeURIComponent(query)}`, {
        cache: 'no-store',
    });

    if (!response.ok) {
        throw new Error('Falha ao carregar simulados');
    }

    return response.json();
}

export async function fetchSimuladoDetail(id: string): Promise<SimuladoDetail | null> {
    const baseUrl = getApiBaseUrl();
    const response = await fetch(`${baseUrl}/api/simulados/${id}`, {
        cache: 'no-store',
    });

    if (response.status === 404) {
        return null;
    }

    if (!response.ok) {
        throw new Error('Falha ao carregar simulado');
    }

    return response.json();
}

export async function fetchQuestoes(id: string): Promise<QuestaoForClient[]> {
    const baseUrl = getApiBaseUrl();
    const response = await fetch(`${baseUrl}/api/simulados/${id}/questoes`, {
        cache: 'no-store',
    });

    if (response.status === 404) {
        return [];
    }

    if (!response.ok) {
        throw new Error('Falha ao carregar questões');
    }

    return response.json();
}

export async function submitRespostas(
    id: string,
    respostas: Record<string, number>,
    tempoGastoSec: number,
): Promise<SubmissionResult> {
    const baseUrl = getApiBaseUrl();
    const response = await fetch(`${baseUrl}/api/simulados/${id}/respostas`, {
        method: 'POST',
        headers: jsonHeaders,
        body: JSON.stringify({ respostas, tempoGastoSec }),
    });

    if (!response.ok) {
        const body = await response.json().catch(() => ({}));
        throw new Error(body?.message ?? 'Falha ao enviar respostas');
    }

    return response.json();
}
