import styles from "./SignUpForm.module.scss"
import {AuthInput} from "@/shared/authInput"
import {useEffect, useState} from "react";
import {SubmitButton} from "@/shared/submitButton";
import {getDoesOwnerExist, registerNewOwner, registerNewUser} from "@/features/signUp";
import {useAuth} from "@/entities/auth";
import {ApiError} from "@/shared/apiError";

type FormErrors = {
    username?: string
    password?: string
    ownersPassword?: string
}


export function SignUp() {
    const [isViewerSelected, setIsViewerSelected] = useState<boolean>(true)
    const [doesOwnerExist, setDoesOwnerExist] = useState<boolean>(false)
    const [errors, setErrors] = useState<FormErrors>({})

    const {login} = useAuth()

    useEffect(() => {
        getDoesOwnerExist()
            .then(setDoesOwnerExist)
            .catch(console.error)
    }, [])

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

        if (doesOwnerExist) {
            const ownersPassword = formData.get("owners-password") as string
            const role = isViewerSelected ? "viewer" : "editor"

            if (ownersPassword.length === 0) {
                newErrors.ownersPassword = "Empty owner's password"
                toAbort = true
            }

            if (toAbort) {
                setErrors(newErrors)
                return
            }

            try {
                const jwt = await registerNewUser(username, password, role, ownersPassword)
                login(jwt)
            } catch (err) {
                if (err instanceof ApiError) {
                    if (err.code === "INVALID_CREDENTIALS") {
                        setErrors({ownersPassword: err.message})
                    } else if (err.code === "LOGIN_ALREADY_EXISTS") {
                        setErrors({username: err.message})
                    }
                } else {
                    setErrors({username: "unknow", password: "unknow", ownersPassword: "unknown"})
                }
            }
        }
        else {

            if (toAbort) {
                setErrors(newErrors)
                return
            }

            try {
                const jwt = await registerNewOwner(username, password)
                login(jwt)
            } catch {
                setErrors({username: "unknow", password: "unknow"})
            }
        }
    }

    return (
        <form className={`${styles.signUp} ${!doesOwnerExist ? styles.noOwner : ""}`} onSubmit={handleSubmit}>
            <AuthInput label="username" isHidden={false} name="username" error={errors.username}/>
            <AuthInput label="password" isHidden={true} name="password" error={errors.password}/>
            {!doesOwnerExist && <span className={styles.note}>You will be signed up as an owner!</span>}
            {doesOwnerExist && <AuthInput label="owner's password" isHidden={true} name="owners-password" error={errors.ownersPassword}/>}
            {doesOwnerExist &&
                <div className={styles.userType}>
                    <label>
                        <input type="radio" name="user-type" checked={isViewerSelected === true} onChange={() => setIsViewerSelected(true)}></input>
                        <span>viewer</span>
                    </label>
                    <label>
                        <input type="radio" name="user-type" checked={isViewerSelected === false} onChange={() => setIsViewerSelected(false)}></input>
                        <span>editor</span>
                    </label>
                </div>
            }
            <SubmitButton text="sign up"/>
        </form>
    );
}

export default SignUp