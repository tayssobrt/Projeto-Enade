'use client';

import { useRouter, useSearchParams } from 'next/navigation';

export default function ResultadoPage() {
  const params = useSearchParams();
  const router = useRouter();

  const score = Number(params.get('score') ?? '0');
  const total = Number(params.get('total') ?? '0');
  const porcentagem = total > 0 ? Math.round((score / total) * 100) : 0;

  return (
    <div className="p-6 max-w-xl mx-auto text-center">
      <h1 className="text-2xl font-bold text-gray-900">Resultado do Simulado</h1>

      <div className="mt-6 rounded-2xl border bg-white p-6 shadow-sm">
        {total > 0 ? (
          <>
            <p className="text-lg text-gray-600">Você acertou</p>
            <p className="mt-2 text-4xl font-bold text-blue-600">{score} / {total}</p>
            <p className="mt-2 text-xl text-gray-700">{porcentagem}%</p>
          </>
        ) : (
          <p className="text-gray-600">Nenhum resultado disponível. Finalize um simulado para ver a pontuação.</p>
        )}
      </div>

      <button
        onClick={() => router.push('/simulados')}
        className="mt-6 rounded-xl bg-blue-600 px-6 py-3 text-white font-semibold transition hover:bg-blue-700"
      >
        Voltar para simulados
      </button>
    </div>
  );
}
