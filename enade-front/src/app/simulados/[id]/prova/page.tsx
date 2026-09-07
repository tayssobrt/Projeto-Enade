'use client';

import { useCallback, useEffect, useMemo, useState } from 'react';
import { useRouter } from 'next/navigation';
import QuestionCard from '@/components/simulado/questoes-card';
import { fetchQuestoes, fetchSimuladoDetail, submitRespostas } from '@/lib/api';
import type { QuestaoForClient } from '@/lib/api';

export default function ProvaPage({ params }: { params: Promise<{ id: string }> }) {
  const router = useRouter();
  const [resolvedParams, setResolvedParams] = useState<{ id: string } | null>(null);
  const [simulado, setSimulado] = useState<any | null>(null);
  const [simuladoQuestoes, setSimuladoQuestoes] = useState<QuestaoForClient[]>([]);
  const [respostas, setRespostas] = useState<Record<string, number>>({});
  const [tempo, setTempo] = useState(0);
  const [initialTempo, setInitialTempo] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let active = true;

    params.then(async (result) => {
      if (!active) return;
      setResolvedParams(result);

      try {
        const [simuladoData, questoesData] = await Promise.all([
          fetchSimuladoDetail(result.id),
          fetchQuestoes(result.id),
        ]);

        if (!active) return;
        setSimulado(simuladoData);
        setSimuladoQuestoes(questoesData);

        if (simuladoData) {
          setTempo(simuladoData.tempo * 60);
          setInitialTempo(simuladoData.tempo * 60);
        }
      } catch (error) {
        console.error(error);
      } finally {
        if (active) setLoading(false);
      }
    });

    return () => {
      active = false;
    };
  }, [params]);

  useEffect(() => {
    if (tempo <= 0) return;
    const interval = setInterval(() => {
      setTempo((current) => (current > 0 ? current - 1 : 0));
    }, 1000);

    return () => clearInterval(interval);
  }, [tempo]);

  const formatTime = (segundos: number) => {
    const min = Math.floor(segundos / 60);
    const sec = segundos % 60;
    return `${min}:${sec.toString().padStart(2, '0')}`;
  };

  const responder = useCallback((id: string, index: number) => {
    setRespostas((prev) => ({ ...prev, [id]: index }));
  }, []);

  const finalizar = useCallback(async () => {
    if (!resolvedParams || !simulado) return;

    const tempoGasto = initialTempo - tempo;

    try {
      const result = await submitRespostas(resolvedParams.id, respostas, tempoGasto);
      router.push(`/simulados/${resolvedParams.id}/resultado?score=${result.score}&total=${result.total}`);
    } catch (error) {
      console.error(error);
    }
  }, [resolvedParams, respostas, router, simulado, tempo, initialTempo]);

  useEffect(() => {
    if (tempo === 0 && simuladoQuestoes.length > 0) {
      void finalizar();
    }
  }, [finalizar, simuladoQuestoes.length, tempo]);

  if (loading) {
    return <p className="p-6">Carregando simulado...</p>;
  }

  if (!resolvedParams || !simulado) {
    return <p className="p-6">Simulado não encontrado</p>;
  }

  return (
    <div className="min-h-screen bg-gray-100">
      <div className="fixed top-0 left-0 w-full bg-white border-b shadow-sm z-50">
        <div className="max-w-4xl mx-auto flex items-center justify-between p-4">
          <div className="flex items-center gap-2 rounded-lg bg-red-100 px-3 py-1 text-red-700 font-semibold">
            ⏱ {formatTime(tempo)}
          </div>

          <div className="text-sm font-medium text-gray-600">
            {Object.keys(respostas).length} / {simuladoQuestoes.length} respondidas
          </div>

          <button
            onClick={finalizar}
            className="rounded-xl bg-blue-600 px-5 py-2 text-white font-semibold shadow-sm transition hover:bg-blue-700"
          >
            Finalizar prova
          </button>
        </div>
      </div>

      <div className="pt-24 pb-10 px-4">
        <div className="mx-auto max-w-3xl space-y-8">
          {simuladoQuestoes.map((q, index) => (
            <div key={q.id}>
              <p className="mb-2 text-sm text-gray-500">Questão {index + 1}</p>
              <QuestionCard
                pergunta={q.pergunta}
                alternativas={q.alternativas}
                selecionada={respostas[q.id]}
                onSelect={(i) => responder(q.id, i)}
              />
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
