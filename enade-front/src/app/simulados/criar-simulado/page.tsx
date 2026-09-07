"use client"

import { useState } from "react"
import { useRouter } from "next/navigation"
import { Loader2 } from "lucide-react"

export default function FormGerarSimulado() {
    const [quantidade, setQuantidade] = useState(10)
    const [isLoading, setIsLoading] = useState(false)
    const router = useRouter()

    async function handleGerarSimulado(e: React.FormEvent) {
        e.preventDefault()
        setIsLoading(true)

        try {
            const response = await fetch(`/api/simulado?quantidadeDeQuestoes=${quantidade}`, {
                method: "POST",
            })

            if (!response.ok) {
                throw new Error("Erro ao gerar simulado")
            }

            const data = await response.json()

            // Redireciona para a tela do simulado criado (caso retorne a ID) ou atualiza a listagem
            if (data?.id) {
                router.push(`/simulados/${data.id}`)
            } else {
                router.refresh()
            }
        } catch (error) {
            console.error("Erro ao tentar gerar o simulado:", error)
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <form
            onSubmit={handleGerarSimulado}
            className="mb-8 flex flex-col gap-4 rounded-2xl border border-gray-200 bg-white p-6 shadow-sm sm:flex-row sm:items-end sm:justify-between"
        >
            <div className="flex flex-col gap-1.5 sm:w-64">
                <label htmlFor="qtd" className="text-sm font-medium text-gray-700">
                    Quantidade de Questões
                </label>
                <select
                    id="qtd"
                    value={quantidade}
                    onChange={(e) => setQuantidade(Number(e.target.value))}
                    disabled={isLoading}
                    className="h-10 rounded-full border border-gray-200 bg-gray-50 px-4 text-sm font-medium text-gray-700 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-500/20"
                >
                    <option value={5}>5 questões</option>
                    <option value={10}>10 questões</option>
                    <option value={15}>15 questões</option>
                    <option value={20}>20 questões</option>
                </select>
            </div>

            <button
                type="submit"
                disabled={isLoading}
                className="flex h-10 items-center justify-center rounded-full bg-[#528cf7] px-6 text-xs font-bold uppercase tracking-wider text-white shadow-sm transition-all hover:bg-blue-600 disabled:opacity-50"
            >
                {isLoading ? (
                    <>
                        <Loader2 className="mr-2 h-4 w-4 animate-spin text-white" />
                        Gerando...
                    </>
                ) : (
                    "Gerar Novo Simulado"
                )}
            </button>
        </form>
    )
}