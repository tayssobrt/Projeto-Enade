'use client';

import { useEffect, useState } from 'react';
import SimuladoCard from './simulado-card';
import type { SimuladoSummary } from '@/lib/api';

export default function SimuladosTable({ query }: { query: string }) {
  const [simulados, setSimulados] = useState<SimuladoSummary[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let active = true;

    async function loadSimulados() {
      setLoading(true);
      const url = `/api/simulados?query=${encodeURIComponent(query)}`;
      const response = await fetch(url, { cache: 'no-store' });
      if (!active) return;

      if (!response.ok) {
        setSimulados([]);
      } else {
        const data = (await response.json()) as SimuladoSummary[];
        setSimulados(data);
      }
      setLoading(false);
    }

    void loadSimulados();

    return () => {
      active = false;
    };
  }, [query]);

  if (loading) {
    return <p className="mt-6 text-sm text-gray-500">Carregando simulados...</p>;
  }

  return (
    <div className="mt-6 flow-root">
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
        {simulados.map((simulado) => (
          <SimuladoCard key={simulado.id} simulado={simulado} />
        ))}
      </div>
    </div>
  );
}
