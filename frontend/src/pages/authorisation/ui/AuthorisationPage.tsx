import styles from "./AuthorisationPage.module.scss"
import {SignInForm} from "@/features/singIn";
import {useState} from "react";
import {SignUpForm} from "@/features/signUp";

export function AuthorisationPage() {
    const [isSignInShown, setIsSignInShown] = useState<boolean>(true)

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