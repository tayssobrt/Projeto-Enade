import Image from "next/image"
import Link from "next/link"
import { LoginForm } from "./_components/login-form"
import IlustracaoEstudantes from "../../public/ilustracao-estudantes.png"
import logoUninassau from "../../public/logonassau.png"

export default function Home() {
  return (
    <div className="relative flex min-h-screen w-full overflow-hidden bg-white">
      {/* Coluna Esquerda - Painel Azul */}
      <div className="relative flex w-1/2 flex-col items-center justify-between bg-[#0c3bb3] p-12 text-white z-10">
        {/* Curva lateral direita */}
        <div className="absolute top-0 right-0 h-full w-[20%] translate-x-1/2 overflow-hidden pointer-events-none">
          <div className="h-full w-full rounded-l-[100%] bg-[#0c3bb3]" />
        </div>

        {/* Chamada para cadastro */}
        <div className="z-10 flex flex-col items-center text-center mt-12 space-y-3">
          <h2 className="text-3xl font-bold tracking-wide">Primeira vez aqui?</h2>
          <p className="text-sm font-light opacity-90">
            Crie sua conta e descubra tudo o que temos para você!
          </p>
          <Link
            href="/signup"
            className="mt-4 rounded-full border-2 border-white px-8 py-2 text-sm font-semibold uppercase tracking-wider text-white transition hover:bg-white hover:text-[#0c3bb3]"
          >
            Criar Conta
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

      {/* Coluna Direita - Formulário de Login */}
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
            <LoginForm />
          </div>

          {/* Logo institucional abaixo */}
          <div className="pt-6 flex items-center justify-center">
            <Image
              src={logoUninassau} // Adicione essa logo na pasta /public
              alt="Uninassau e Ser Educacional"
              width={130}
              height={45}
              className="object-contain"
            />
          </div>
        </div>
      </div>
    </div>
  )
}