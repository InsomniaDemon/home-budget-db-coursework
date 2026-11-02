import styles from "./SignInForm.module.scss"
import {FormInput} from "@/shared/formInput";
import SubmitButton from "@/shared/submitButton/ui/SubmitButton.tsx";

export function SignIn() {
    return (
        <form className={styles.signIn}>
            <FormInput label="login" isPassword={false}/>
            <FormInput label="password" isPassword={true}/>
            <SubmitButton text="sign in"/>
        </form>
    );
}

export default SignIn