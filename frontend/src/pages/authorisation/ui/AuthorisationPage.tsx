import styles from "./AuthorisationPage.module.scss"
import {SignInForm} from "@/features/singIn";
import {useState} from "react";
import {SignUpForm} from "@/features/signUp";

export function AuthorisationPage() {
    const [isSignInShown, setIsSignInShown] = useState<boolean>(true)

    return (
        <div className={styles.auth}>
            <div>
                <label className={styles.chosenForm} >
                    <input type="radio" name="page" checked={isSignInShown === true} onChange={() => setIsSignInShown(true)}></input>
                    <span>Корзина</span>
                </label>
                <label className={styles.chosenForm}>
                    <input type="radio" name="page" checked={isSignInShown === false} onChange={() => setIsSignInShown(false)}></input>
                    <span>История заказов</span>
                </label>
            </div>

            {isSignInShown && <SignInForm/>}
            {!isSignInShown && <SignUpForm/>}
        </div>
    );
}

export default AuthorisationPage