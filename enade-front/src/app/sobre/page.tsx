import Image from "next/image"
import Link from "next/link"
import Navbar from "@/components/dashboard/navbar"

import logoUninassau from "../../../public/logonassau.png"

export default function SobrePage() {
    return (
        <div className="flex min-h-screen flex-col bg-gray-50">
            {/* Navbar Superior */}
            <Navbar />

            {/* Conteúdo Principal */}
            <main className="flex-1 py-12 px-6 md:px-16 lg:px-24 max-w-6xl mx-auto w-full space-y-10">

                {/* Banner do Título */}
                <div className="bg-[#0c3bb3] rounded-3xl p-8 md:p-12 text-white shadow-md relative overflow-hidden">
                    <div className="relative z-10 space-y-3 max-w-2xl">
                        <h1 className="text-3xl md:text-4xl font-extrabold tracking-tight">
                            Sobre o Enade Quiz
                        </h1>
                        <p className="text-sm md:text-base font-light text-blue-100 leading-relaxed">
                            Conheça a proposta da nossa plataforma e entenda a importância do Exame Nacional de Desempenho dos Estudantes para a sua formação acadêmica.
                        </p>
                    </div>
                </div>

                {/* Grade de Seções */}
                <div className="grid grid-cols-1 md:grid-cols-2 gap-8">

                    {/* Card: O que é o ENADE */}
                    <div className="bg-white rounded-2xl p-6 md:p-8 border border-gray-200 shadow-sm space-y-4">
                        <h2 className="text-xl font-bold text-gray-900">O que é o ENADE?</h2>
                        <p className="text-sm text-gray-600 leading-relaxed">
                            O Exame Nacional de Desempenho dos Estudantes (ENADE) avalia o rendimento dos alunos dos cursos de graduação em relação aos conteúdos programáticos previstos nas diretrizes curriculares, suas habilidades e competências para a atuação profissional.
                        </p>
                    </div>

                    {/* Card: Intuito do Projeto */}
                    <div className="bg-white rounded-2xl p-6 md:p-8 border border-gray-200 shadow-sm space-y-4">
                        <h2 className="text-xl font-bold text-gray-900">Intuito do Projeto</h2>
                        <p className="text-sm text-gray-600 leading-relaxed">
                            O <strong>ENADE.Quiz</strong> foi idealizado para proporcionar uma preparação dinâmica e acessível aos estudantes. Através de simulações com questões de provas anteriores, o aluno pratica o formato da prova, avalia seu desempenho e reforça pontos de melhoria de forma prática.
                        </p>
                    </div>

                </div>

                {/* Chamada para Ação */}
                <div className="bg-white rounded-2xl p-8 border border-gray-200 shadow-sm flex flex-col sm:flex-row items-center justify-between gap-6">
                    <div className="space-y-1 text-center sm:text-left">
                        <h3 className="text-lg font-bold text-gray-900">Pronto para testar seus conhecimentos?</h3>
                        <p className="text-xs text-gray-500">Acesse nossos simulados e comece a treinar agora mesmo.</p>
                    </div>
                    <Link
                        href="/simulados"
                        className="rounded-full bg-[#528cf7] hover:bg-blue-600 px-8 py-3 text-xs font-bold uppercase tracking-wider text-white shadow-md transition-all"
                    >
                        Iniciar Simulado
                    </Link>
                </div>

            </main>

            {/* Rodapé / Copyrights */}
            <footer className="w-full border-t border-gray-200 bg-white py-6 px-6 md:px-16">
                <div className="max-w-6xl mx-auto flex flex-col md:flex-row items-center justify-between gap-4 text-center md:text-left">

                    <div className="flex items-center space-x-3">
                        <Image
                            src={logoUninassau}
                            alt="Uninassau e Ser Educacional"
                            width={110}
                            height={38}
                            className="object-contain"
                        />
                    </div>

                    <div className="text-xs text-gray-500 space-y-1">
                        <p>&copy; {new Date().getFullYear()} ENADE.Quiz. Todos os direitos reservados.</p>
                        <p className="text-gray-400">Desenvolvido para apoio educacional e preparação acadêmica.</p>
                    </div>

                </div>
            </footer>
        </div>
    )
}