import Image from "next/image"
import Link from "next/link"
import Navbar from "@/components/dashboard/navbar"

import IlustracaoEstudantes from "../../../public/ilustracao-estudantes.png"
import logoUninassau from "../../../public/logonassau.png"

export default function DashboardPage() {
    return (
        <div className="flex min-h-screen flex-col bg-white">
            {/* Navbar Superior */}
            <Navbar />

            {/* Conteúdo Principal (Hero Section) */}
            <main className="relative flex flex-1 items-center justify-between overflow-hidden px-8 md:px-16 lg:px-24">
                {/* Coluna da Esquerda: Textos e Ações */}
                <div className="z-10 max-w-lg space-y-6 py-12">
                    <h1 className="text-4xl font-extrabold text-gray-900 tracking-tight">
                        Bem-vindo!!!
                    </h1>

                    <p className="text-base leading-relaxed text-gray-600">
                        Estamos felizes em apresentar o <strong>ENADE.QUIZ</strong>, uma plataforma desenvolvida para
                        ajudar na sua preparação para o Exame Nacional de Desempenho dos Estudantes
                        (ENADE). Aqui você terá acesso a quizzes interativos com perguntas de provas
                        anteriores, proporcionando uma forma dinâmica e eficaz de reforçar seu
                        conhecimento.
                    </p>

                    <div className="pt-2">
                        <Link
                            href="/simulados"
                            className="inline-flex h-11 items-center justify-center rounded-full bg-[#528cf7] px-8 text-sm font-bold uppercase tracking-wider text-white shadow-md transition-all hover:bg-blue-600"
                        >
                            Iniciar Simulado
                        </Link>
                    </div>

                    {/* Logo da Uninassau / Ser Educacional */}
                    <div className="pt-6">
                        <Image
                            src={logoUninassau}
                            alt="Uninassau e Ser Educacional"
                            width={140}
                            height={50}
                            className="object-contain"
                        />
                    </div>
                </div>

                {/* Coluna da Direita: Container Azul Arredondado com Ilustração */}
                <div className="relative flex items-center justify-center h-[85vh] w-[45%] max-w-xl">
                    {/* Fundo Azul Arredondado */}
                    <div className="absolute inset-0 bg-[#0c3bb3] rounded-tl-[120px] rounded-bl-[120px] rounded-tr-[40px] rounded-br-[40px] shadow-lg" />

                    {/* Imagem dos Estudantes */}
                    <div className="relative z-10 h-full w-full p-6">
                        <Image
                            src={IlustracaoEstudantes}
                            alt="Estudantes Enade Quiz"
                            fill
                            className="object-contain p-4"
                            priority
                        />
                    </div>
                </div>
            </main>
        </div>
    )
}