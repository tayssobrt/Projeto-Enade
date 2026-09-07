import { headers } from 'next/headers';
import { auth } from '@/lib/auth';
import { ButtonSignOut } from '@/app/_components/button-logout/button-signout';
import { redirect } from 'next/navigation';
import { prisma } from '@/lib/prisma';
import SideNav from '@/components/dashboard/sidenav';

export default async function Perfil() {
    const session = await auth.api.getSession({
        headers: await headers(),
    });

    if (!session) {
        redirect('/');
    }

    const [user, stats] = await Promise.all([
        prisma.user.findUnique({
            where: { id: session.user.id },
            select: {
                id: true,
                name: true,
                email: true,
            },
        }),
        prisma.simuladoAttempt.aggregate({
            where: { userId: session.user.id },
            _count: { _all: true },
            _sum: { tempoGastoSec: true, score: true },
        }),
    ]);

    if (!user) {
        redirect('/');
    }

    const totalTempo = stats._sum.tempoGastoSec ?? 0;
    const totalSimulados = stats._count._all;
    const totalScore = stats._sum.score ?? 0;

    return (
        <div className="flex min-h-screen bg-gray-100">
            <div className="hidden md:block w-64 border-r bg-white">
                <SideNav />
            </div>

            <div className="flex-1 flex justify-center pt-10 px-4">
                <div className="w-full max-w-xl bg-white border border-gray-200 rounded-2xl shadow-sm p-6">
                    <div className="mb-6">
                        <h1 className="text-2xl font-semibold text-gray-800">Meu Perfil</h1>
                        <p className="text-sm text-gray-500 mt-1">Gerencie suas informações pessoais</p>
                    </div>

                    <div className="grid gap-3 mb-6">
                        <div className="p-3 border rounded-lg bg-gray-50">
                            <span className="text-xs text-gray-500">Nome</span>
                            <p className="text-base font-medium text-gray-900">{user.name}</p>
                        </div>

                        <div className="p-3 border rounded-lg bg-gray-50">
                            <span className="text-xs text-gray-500">Email</span>
                            <p className="text-base font-medium text-gray-900">{user.email}</p>
                        </div>

                        <div className="p-3 border rounded-lg bg-gray-50">
                            <span className="text-xs text-gray-500">Simulados realizados</span>
                            <p className="text-base font-medium text-gray-900">{totalSimulados}</p>
                        </div>

                        <div className="p-3 border rounded-lg bg-gray-50">
                            <span className="text-xs text-gray-500">Tempo total</span>
                            <p className="text-base font-medium text-gray-900">{Math.floor(totalTempo / 60)} min</p>
                        </div>

                        <div className="p-3 border rounded-lg bg-gray-50">
                            <span className="text-xs text-gray-500">Pontuação total</span>
                            <p className="text-base font-medium text-gray-900">{totalScore}</p>
                        </div>
                    </div>

                    <div className="mt-6 flex justify-between items-center">
                        <span className="text-xs text-gray-400">Última atualização agora</span>
                        <ButtonSignOut />
                    </div>
                </div>
            </div>
        </div>
    );
}
