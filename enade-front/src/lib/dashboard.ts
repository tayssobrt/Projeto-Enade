import { prisma } from '@/lib/prisma';

export type LatestSimuladoInfo = {
    id: string;
    titulo: string;
    nota: number;
    tempoMinutos: number;
    data: string;
};

export type DashboardStats = {
    simuladosRealizados: number;
    simuladosPendentes: number;
    mediaAcertos: number;
    tempoMedio: number;
    latestSimulados: LatestSimuladoInfo[];
};

export async function getDashboardStats(userId: string): Promise<DashboardStats> {
    const completedAttempts = await prisma.simuladoAttempt.findMany({
        where: {
            userId,
            finishedAt: { not: null },
        },
        include: {
            simulado: {
                select: {
                    titulo: true,
                    quantidadeQuestoes: true,
                },
            },
        },
        orderBy: { finishedAt: 'desc' },
    });

    const simuladosPendentes = await prisma.simulado.count({
        where: {
            NOT: {
                tentativas: {
                    some: {
                        userId,
                        finishedAt: { not: null },
                    },
                },
            },
        },
    });

    const totalQuestions = completedAttempts.reduce(
        (acc, attempt) => acc + attempt.simulado.quantidadeQuestoes,
        0,
    );

    const totalScore = completedAttempts.reduce((acc, attempt) => acc + attempt.score, 0);
    const totalTempoSec = completedAttempts.reduce((acc, attempt) => acc + attempt.tempoGastoSec, 0);

    return {
        simuladosRealizados: completedAttempts.length,
        simuladosPendentes,
        mediaAcertos: totalQuestions > 0 ? Math.round((totalScore / totalQuestions) * 100) : 0,
        tempoMedio:
            completedAttempts.length > 0
                ? Math.round(totalTempoSec / completedAttempts.length / 60)
                : 0,
        latestSimulados: completedAttempts.slice(0, 4).map((attempt) => ({
            id: attempt.simuladoId,
            titulo: attempt.simulado.titulo,
            nota:
                attempt.simulado.quantidadeQuestoes > 0
                    ? Math.round((attempt.score / attempt.simulado.quantidadeQuestoes) * 100)
                    : 0,
            tempoMinutos: Math.round(attempt.tempoGastoSec / 60),
            data: attempt.finishedAt?.toISOString() ?? new Date().toISOString(),
        })),
    };
}
