import styles from "./SignUpForm.module.scss"
import {FormInput} from "@/shared/formInput"
import {useMemo, useState} from "react";
import SubmitButton from "@/shared/submitButton/ui/SubmitButton.tsx";

export function SignUp() {
    const [isViewerSelected, setIsViewerSelected] = useState<boolean>(true)

    const isOwnerExists = useMemo(() => true, [])

    return (
        <div className={`${styles.signUp} ${!isOwnerExists ? styles.noOwner : ""}`}>
            <FormInput label="login" isPassword={false}/>
            <FormInput label="password" isPassword={true}/>
            {!isOwnerExists && <span className={styles.note}>You will be signed up as an owner!</span>}
            {isOwnerExists && <FormInput label="owner's password" isPassword={true}/>}
            {isOwnerExists &&
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
        </div>
    );
}

export default SignUp