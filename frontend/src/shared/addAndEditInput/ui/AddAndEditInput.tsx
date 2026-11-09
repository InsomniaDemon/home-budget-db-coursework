import styles from "./AddAndEditInput.module.scss"

export function AddAndEditInput({label, name, type = "text", value = "", error}: {label: string, name: string, type?: string, value?: string | number, error?: string}) {

    return (
        <label className={styles.addAndEditInput}>
            <div className={styles.spans}>
                <span>{label}</span>
                <span className={styles.type}>${type}</span>
            </div>
            <span>:</span>
            <div className={styles.field}>
                <input name={name} type={type} defaultValue={value} className={error ? styles.errorInput : ""}/>
                {error && <span className={styles.error}>{error}</span>}
            </div>
        </label>
    );
}

export default AddAndEditInput