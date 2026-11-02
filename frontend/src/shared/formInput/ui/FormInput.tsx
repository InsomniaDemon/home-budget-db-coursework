import styles from "./FormInput.module.scss"

export function FormInput({label, isPassword}: {label: string, isPassword: boolean}) {
    return (
        <label className={styles.formInput}>
            <span>{label}</span>
            <input type={isPassword && "password"}/>
        </label>
    );
}

export default FormInput