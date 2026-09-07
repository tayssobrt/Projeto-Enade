"use client"

import Link from "next/link"
import { usePathname } from "next/navigation"
import clsx from "clsx"

const links = [
    { name: "Início", href: "/inicio" },
    { name: "Simulado", href: "/simulados" },
    { name: "Sobre", href: "/sobre" },
]

export default function NavLinks() {
    const pathname = usePathname()

    return (
        <>
            {links.map((link) => {
                const isActive = pathname === link.href

                return (
                    <Link
                        key={link.name}
                        href={link.href}
                        className={clsx(
                            "relative py-1 text-sm font-medium transition-colors hover:text-blue-200",
                            {
                                "text-white font-semibold": isActive,
                                "text-gray-200": !isActive,
                            }
                        )}
                    >
                        {link.name}
                        {/* Linha indicadora de link ativo abaixo do texto */}
                        {isActive && (
                            <span className="absolute bottom-0 left-0 h-[2px] w-full rounded-full bg-white" />
                        )}
                    </Link>
                )
            })}
        </>
    )
}