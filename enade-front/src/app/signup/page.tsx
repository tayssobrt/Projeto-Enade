import Image from "next/image"
import Link from "next/link"
import { SignupForm } from "./_components/signup-form"

import IlustracaoEstudantes from "../../../public/ilustracao-estudantes.png"
import logoUninassau from "../../../public/logonassau.png"


export default function Signup() {
  return (
    <div className="relative flex min-h-screen w-full overflow-hidden bg-white">
      {/* Coluna Esquerda - Formulário de Cadastro */}
      <div className="flex w-1/2 flex-col items-center justify-center p-8 z-0">
        <div className="w-full max-w-sm space-y-6 flex flex-col items-center">
          {/* Logo Enade Quiz */}
          <div className="text-center">
            <h1 className="text-4xl font-extrabold tracking-tight text-black">
              ENADE<span className="text-[#0c3bb3]">.</span>
            </h1>
            <p className="text-xl font-bold tracking-wide text-black text-right -mt-1">
              Quiz
            </p>
          </div>

          {/* Componente do Formulário */}
          <div className="w-full">
            <SignupForm />
          </div>

          {/* Logo institucional */}
          <div className="pt-4 flex items-center justify-center">
            <Image
              src={logoUninassau}
              alt="Uninassau e Ser Educacional"
              width={130}
              height={45}
              className="object-contain"
            />
          </div>
        </div>
      </div>

      {/* Coluna Direita - Painel Azul */}
      <div className="relative flex w-1/2 flex-col items-center justify-between bg-[#0c3bb3] p-12 text-white z-10">
        {/* Curva lateral esquerda */}
        <div className="absolute top-0 left-0 h-full w-[20%] -translate-x-1/2 overflow-hidden pointer-events-none">
          <div className="h-full w-full rounded-r-[100%] bg-[#0c3bb3]" />
        </div>

        {/* Chamada para Login */}
        <div className="z-10 flex flex-col items-center text-center mt-12 space-y-3">
          <h2 className="text-3xl font-bold tracking-wide">Já tem cadastro?</h2>
          <p className="text-sm font-light opacity-90 max-w-xs">
            Se você já é nosso usuário, clique aqui para fazer login.
          </p>
          <Link
            href="/"
            className="mt-4 rounded-full border-2 border-white px-8 py-2 text-sm font-semibold uppercase tracking-wider text-white transition hover:bg-white hover:text-[#0c3bb3]"
          >
            LOGIN
          </Link>
        </div>

        {/* Ilustração */}
        <div className="z-10 relative h-80 w-full max-w-md">
          <Image
            src={IlustracaoEstudantes}
            alt="Estudantes Enade Quiz"
            fill
            className="object-contain"
            priority
          />
        </div>
      </div>
    </div>
  )
}