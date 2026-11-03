import { createContext, useContext, useState } from "react"
import * as React from "react"

interface AuthContextType {
    jwt: string
    setJwt: React.Dispatch<React.SetStateAction<string | undefined>>
    login: (jwt: string | null) => void
    logout: () => void
}


const AuthContext = createContext<AuthContextType | null>(null)

export function AuthProvider({children}: {children: React.ReactNode}) {
    const [jwt, setJwt] = useState<string | null>(() => {
            return localStorage.getItem("jwt")
        }
    )

    const login = (jwt: string | null) => {
        localStorage.setItem("jwt", jwt)
        setJwt(jwt)
    }

    const logout = () => {
        localStorage.setItem("jwt", null)
        setJwt(null)
    }

    return (
        <AuthContext.Provider value={{ jwt, setJwt, login, logout}}>
            {children}
        </AuthContext.Provider>
    )
}

export function useAuth() {
    const context = useContext(AuthContext);

    if (!context) {
        throw new Error("useAuth must be used inside AuthProvider");
    }

    return context;
}

