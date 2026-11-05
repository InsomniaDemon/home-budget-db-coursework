import styles from "./AuthInput.module.scss"

export function FormInput({label, isHidden, name}: {label: string, isHidden: boolean, name: string}) {
    return (
        <label className={styles.authInput}>
            <span>{label}</span>
            <input type={isHidden ? "password" : "text"} name={name}/>
        </label>
    );
}

export default FormInput