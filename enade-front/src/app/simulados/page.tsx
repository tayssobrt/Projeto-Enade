import Table from '@/components/simulado/table';
import FormGerarSimulado from '@/app/simulados/criar-simulado/page'; // Import do formulário

import { Metadata } from 'next';
import Navbar from '@/components/dashboard/navbar';

export const metadata: Metadata = {
  title: 'Simulados',
};

export default async function Page(props: {
  searchParams?: Promise<{
    query?: string;
  }>;
}) {
  const searchParams = await props.searchParams;
  const query = searchParams?.query || '';

  return (
    <div className="flex min-h-screen flex-col bg-gray-50">
      {/* Navbar Superior */}
      <Navbar />

      {/* Conteúdo Principal */}
      <main className="grow p-6 md:p-12">
        <div className="mx-auto max-w-7xl">
          <div className="mb-6 flex w-full items-center justify-between">
            <h1 className="text-2xl font-semibold text-gray-800">Simulados</h1>
          </div>

          {/* Formulário para criar novo simulado */}
          <FormGerarSimulado />

          {/* Tabela com a lista dos simulados salvos */}
          <Table query={query} />
        </div>
      </main>
    </div>
  );
}