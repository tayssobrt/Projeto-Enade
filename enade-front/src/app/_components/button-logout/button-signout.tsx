"use client"

import { Button } from "@/components/ui/button";
import { useRouter } from "next/navigation";
import { authClient } from "@/lib/auth-client";


export function ButtonSignOut() {
  const router = useRouter();

  async function signOut() {
    await authClient.signOut({
      fetchOptions: {
        onSuccess: () => {
          router.replace("/")
        }
      }
    })
  }

  return (
    <Button onClick={signOut}>
      Sair da conta
    </Button>
  );
}