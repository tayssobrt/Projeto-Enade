import Link from 'next/link';
import { ArrowRightIcon } from '@heroicons/react/24/outline';
import type { SimuladoSummary } from '@/lib/api';

export default function SimuladoCard({ simulado }: { simulado: SimuladoSummary }) {
    const notePercent = simulado.lastAttempt
        ? Math.round((simulado.lastAttempt.score / simulado.quantidadeQuestoes) * 100)
        : null;

    return (
        <Link
            href={`/simulados/${simulado.id}`}
            className="group rounded-2xl bg-white p-5 shadow-sm border transition hover:-translate-y-1 hover:shadow-lg"
        >
            <div className="flex items-start justify-between gap-4">
                <div>
                    <h3 className="text-lg font-semibold text-gray-900 group-hover:text-blue-600 transition">
                        {simulado.titulo}
                    </h3>
                    <p className="text-sm text-gray-500 mt-1">{simulado.tema}</p>
                </div>
                <span
                    className={`rounded-full px-2 py-1 text-xs font-semibold ${simulado.status === 'concluido'
                        ? 'bg-green-100 text-green-700'
                        : 'bg-yellow-100 text-yellow-700'
                        }`}
                >
                    {simulado.status === 'concluido' ? 'Concluído' : 'Pendente'}
                </span>
            </div>

            <div className="mt-5 flex flex-wrap items-center justify-between gap-3 text-sm text-gray-500">
                <span className="rounded-full bg-gray-100 px-3 py-1 font-medium">
                    {simulado.quantidadeQuestoes} questões
                </span>
                <span>{new Date(simulado.data).toLocaleDateString('pt-BR')}</span>
            </div>

            {notePercent !== null ? (
                <p className="mt-4 text-sm font-medium text-gray-700">
                    Nota obtida: {notePercent}%
                </p>
            ) : null}

            <div className="mt-5 flex items-center justify-between gap-3 text-sm font-medium">
                <span
                    className={`rounded-lg px-3 py-1 transition ${simulado.status === 'concluido'
                        ? 'bg-gray-200 text-gray-700'
                        : 'bg-blue-600 text-white group-hover:bg-blue-700'
                        }`}
                >
                    {simulado.status === 'concluido' ? 'Revisar' : 'Iniciar'}
                </span>
                <ArrowRightIcon className="h-4 w-4 text-blue-600 transition group-hover:translate-x-1" />
            </div>
        </Link>
    );
}
