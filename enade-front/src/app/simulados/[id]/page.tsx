import { headers } from 'next/headers';
import { redirect } from 'next/navigation';
import { StartSimuladoButton } from '@/components/simulado/start-button';
import { auth } from '@/lib/auth';
import { prisma } from '@/lib/prisma';

export default async function SimuladoDetalhe({ params }: { params: Promise<{ id: string }> }) {
  const resolvedParams = await params;
  const session = await auth.api.getSession({ headers: await headers() });

  if (!session) {
    redirect('/');
  }

  const simulado = await prisma.simulado.findUnique({
    where: { id: resolvedParams.id },
  });

  if (!simulado) {
    return <p className="p-6">Simulado não encontrado</p>;
  }

  return (
    <div className="p-6 max-w-3xl mx-auto">
      <div className="bg-white rounded-2xl border p-6 shadow-sm">
        <h1 className="text-2xl font-bold text-gray-900">{simulado.titulo}</h1>
        <p className="text-gray-500 mt-1">{simulado.tema}</p>

        <div className="grid grid-cols-2 gap-4 mt-6">
          <div className="rounded-xl bg-gray-50 p-4 text-center">
            <p className="text-sm text-gray-500">Questões</p>
            <p className="text-xl font-semibold text-gray-900">{simulado.quantidadeQuestoes}</p>
          </div>

          <div className="rounded-xl bg-gray-50 p-4 text-center">
            <p className="text-sm text-gray-500">Tempo</p>
            <p className="text-xl font-semibold text-gray-900">{simulado.tempoMinutos} min</p>
          </div>
        </div>

        <div className="mt-6 rounded-xl border border-blue-100 bg-blue-50 p-4 text-sm text-blue-900">
          <p className="font-medium mb-1">Instruções:</p>
          <ul className="list-disc ml-4 space-y-1">
            <li>Leia cada questão com atenção</li>
            <li>Você pode revisar antes de finalizar</li>
            <li>O tempo começa ao iniciar</li>
          </ul>
        </div>

        <StartSimuladoButton simuladoId={simulado.id} />
      </div>
    </div>
  );
}
