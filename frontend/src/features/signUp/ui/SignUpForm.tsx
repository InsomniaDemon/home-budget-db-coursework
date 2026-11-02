import styles from "./SignUpForm.module.scss"
import {FormInput} from "@/shared/formInput"
import {useEffect, useState} from "react";
import {SubmitButton} from "@/shared/submitButton";
import {getDoesOwnerExist} from "@/features/signUp";

export function SignUp() {
    const [isViewerSelected, setIsViewerSelected] = useState<boolean>(true)
    const [doesOwnerExist, setDoesOwnerExist] = useState<boolean>(false)

    useEffect(() => {
        getDoesOwnerExist()
            .then(setDoesOwnerExist)
            .catch(console.error)
    }, [])

    return (
        <div className={`${styles.signUp} ${!doesOwnerExist ? styles.noOwner : ""}`}>
            <FormInput label="login" isHidden={false}/>
            <FormInput label="password" isHidden={true}/>
            {!doesOwnerExist && <span className={styles.note}>You will be signed up as an owner!</span>}
            {doesOwnerExist && <FormInput label="owner's password" isHidden={true}/>}
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
        </div>
    );
}

export default SignUp