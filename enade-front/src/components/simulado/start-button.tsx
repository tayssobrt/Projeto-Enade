'use client';

import { useRouter } from 'next/navigation';

type StartButtonProps = {
    simuladoId: string;
};

export function StartSimuladoButton({ simuladoId }: StartButtonProps) {
    const router = useRouter();

    return (
        <button
            onClick={() => router.push(`/simulados/${simuladoId}/prova`)}
            className="mt-6 w-full rounded-xl bg-blue-600 px-4 py-3 text-white font-semibold transition hover:bg-blue-700"
        >
            Iniciar simulado
        </button>
    );
}
