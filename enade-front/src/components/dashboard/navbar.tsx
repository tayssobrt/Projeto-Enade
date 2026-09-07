import Link from 'next/link'
import Image from 'next/image'
import NavLinks from '@/components/dashboard/navlinks'
import { ButtonSignOut } from './button-signout'

export default function Navbar() {
    return (
        <header className="flex h-16 w-full items-center justify-between bg-[#0c3bb3] px-6 text-white shadow-md">
            {/* Logo Enade Quiz */}
            <Link href="/" className="flex items-center space-x-2">
                <div className="flex flex-col text-left leading-none">
                    <span className="text-xl font-extrabold tracking-tight text-white">
                        Enade<span className="text-blue-300">.</span>
                    </span>
                    <span className="text-xs font-semibold text-gray-200">Quiz</span>
                </div>
            </Link>

            {/* Links Centrais */}
            <nav className="flex items-center space-x-8">
                <NavLinks />
            </nav>

            {/* Ações da Direita */}
            <div className="flex items-center space-x-3">
                <Link
                    href="/perfil"
                    className="rounded-full border border-white/60 px-5 py-1.5 text-xs font-medium uppercase tracking-wider text-white transition hover:bg-white hover:text-[#0c3bb3]"
                >
                    Perfil
                </Link>
            </div>
        </header>
    )
}