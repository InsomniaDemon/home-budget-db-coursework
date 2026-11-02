import styles from "./SignInForm.module.scss"
import {FormInput} from "@/shared/formInput";
import {SubmitButton} from "@/shared/submitButton";

export function SignIn() {
    return (
        <form className={styles.signIn}>
            <FormInput label="login" isHidden={false}/>
            <FormInput label="password" isHidden={true}/>
            <SubmitButton text="sign in"/>
        </form>
    );
}

export default SignIn