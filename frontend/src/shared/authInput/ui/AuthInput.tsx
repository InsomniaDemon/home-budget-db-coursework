import styles from "./AuthInput.module.scss"

export function AuthInput({label, isHidden, name, error}: {label: string, isHidden: boolean, name: string, error?: string}) {
    return (
        <label className={styles.authInput}>
            <span>{label}</span>
            <input type={isHidden ? "password" : "text"} name={name} className={error ? styles.errorInput : ""}/>
            {error && <span className={styles.error}>{error}</span>}
        </label>
    );
}

export default AuthInput