import styles from "./AuthorisationPage.module.scss"
import {SignInForm} from "@/features/signIn";
import {useEffect, useState} from "react";
import {SignUpForm} from "@/features/signUp";
import {useNavigate} from "react-router-dom";
import {useAuth} from "@/entities/auth";

export function AuthorisationPage() {
    const [isSignInShown, setIsSignInShown] = useState<boolean>(true)

    const navigate = useNavigate()
    const {jwt} = useAuth()

    useEffect(() => {
        if (jwt) {
            navigate("/operations")
        }
    }, [jwt, navigate])

    return (
        <div className={styles.auth}>
            <div className={styles.options}>
                <label className={styles.chosenForm} >
                    <input type="radio" name="page" checked={isSignInShown === true} onChange={() => setIsSignInShown(true)}></input>
                    <span>Sign in</span>
                </label>
                <label className={styles.chosenForm}>
                    <input type="radio" name="page" checked={isSignInShown === false} onChange={() => setIsSignInShown(false)}></input>
                    <span>Sign up</span>
                </label>
            </div>

            <div className={styles.authForm}>
                {isSignInShown && <SignInForm/>}
                {!isSignInShown && <SignUpForm/>}
            </div>
        </div>
    );
}

export default AuthorisationPage