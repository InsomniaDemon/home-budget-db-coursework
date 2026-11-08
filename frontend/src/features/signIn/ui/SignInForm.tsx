import styles from "./SignInForm.module.scss"
import {AuthInput} from "@/shared/authInput";
import {SubmitButton} from "@/shared/submitButton";
import {useAuth} from "@/entities/auth";
import {signInUser} from "@/features/signIn";
import {useState} from "react";
import {ApiError} from "@/shared/apiError";

type FormErrors = {
    username?: string
    password?: string
}

export function SignIn() {
    const {login} = useAuth()
    const [errors, setErrors] = useState<FormErrors>({})

    const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
        e.preventDefault()

        setErrors({})
        const newErrors: FormErrors = {}
        let toAbort: boolean = false

        const formData = new FormData(e.currentTarget)

        const username = formData.get("username") as string
        const password = formData.get("password") as string

        if (username.length === 0) {
            newErrors.username = "Empty username"
            toAbort = true
        }

        if (password.length === 0) {
            newErrors.password = "Empty password"
            toAbort = true
        }

        if (toAbort) {
            setErrors(newErrors)
            return
        }

        try {
            const jwt = await signInUser(username, password)
            login(jwt)
        } catch (err) {
            if (err instanceof ApiError) {
                setErrors({username: err.message, password: err.message})
            } else {
                setErrors({username: "unknow", password: "unknow"})
            }
        }
    }

    return (
        <form className={styles.signIn} onSubmit={handleSubmit}>
            <AuthInput label="username" isHidden={false} name="username" error={errors.username}/>
            <AuthInput label="password" isHidden={true} name="password" error={errors.password}/>
            <SubmitButton text="sign in"/>
        </form>
    );
}

export default SignIn