import styles from "./SignUpForm.module.scss"
import {FormInput} from "@/shared/formInput"
import {useEffect, useState} from "react";
import {SubmitButton} from "@/shared/submitButton";
import {getDoesOwnerExist, registerNewOwner, registerNewUser} from "@/features/signUp";
import {useAuth} from "@/entities/auth";

export function SignUp() {
    const [isViewerSelected, setIsViewerSelected] = useState<boolean>(true)
    const [doesOwnerExist, setDoesOwnerExist] = useState<boolean>(false)

    const {login} = useAuth()

    useEffect(() => {
        getDoesOwnerExist()
            .then(setDoesOwnerExist)
            .catch(console.error)
    }, [])

    const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
        e.preventDefault()

        const formData = new FormData(e.currentTarget)

        const username = formData.get("login") as string
        const password = formData.get("password") as string

        if (doesOwnerExist) {
            const ownersPassword = formData.get("owners-password") as string
            const role = isViewerSelected ? "viewer" : "editor"
            try {
                const jwt = await registerNewUser(username, password, role, ownersPassword)
                login(jwt)
            } catch (err) {
                console.log(err)
            }
        }
        else {
            try {
                const jwt = await registerNewOwner(username, password)
                login(jwt)
            } catch (err) {
                console.log(err)
            }
        }
    }

    return (
        <form className={`${styles.signUp} ${!doesOwnerExist ? styles.noOwner : ""}`} onSubmit={handleSubmit}>
            <FormInput label="username" isHidden={false} name="login"/>
            <FormInput label="password" isHidden={true} name="password"/>
            {!doesOwnerExist && <span className={styles.note}>You will be signed up as an owner!</span>}
            {doesOwnerExist && <FormInput label="owner's password" isHidden={true} name="owners-password"/>}
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