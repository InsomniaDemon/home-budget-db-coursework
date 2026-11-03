import styles from "./SignInForm.module.scss"
import {FormInput} from "@/shared/formInput";
import {SubmitButton} from "@/shared/submitButton";
import {useAuth} from "@/entities/auth";
import {signInUser} from "@/features/signIn";

export function SignIn() {
    const {login} = useAuth()

    const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
        e.preventDefault()

        const formData = new FormData(e.currentTarget)

        const username = formData.get("username") as string
        const password = formData.get("password") as string

        console.log(username)
        console.log(password)

        try {
            const jwt = await signInUser(username, password)
            login(jwt)
        } catch (err) {
            console.log(err)
        }
    }

    return (
        <form className={styles.signIn} onSubmit={handleSubmit}>
            <FormInput label="username" isHidden={false} name="username"/>
            <FormInput label="password" isHidden={true} name="password"/>
            <SubmitButton text="sign in"/>
        </form>
    );
}

export default SignIn