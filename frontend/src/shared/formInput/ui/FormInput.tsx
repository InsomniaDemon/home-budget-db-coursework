import styles from "./FormInput.module.scss"

export function FormInput({label, isHidden}: {label: string, isHidden: boolean}) {
    return (
        <label className={styles.formInput}>
            <span>{label}</span>
            <input type={isHidden ? "password" : "text"} />
        </label>
    );
}

export default FormInput