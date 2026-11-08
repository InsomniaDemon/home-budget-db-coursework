import styles from "./AddAndEditInput.module.scss"

export function AddAndEditInput({label, name, type = "text", value = ""}: {label: string, name: string, type?: string, value?: string | number}) {

    return (
        <label className={styles.addAndEditInput}>
            <div className={styles.spans}>
                <span>{label}</span>
                <span className={styles.type}>${type}</span>
            </div>
            <span>:</span>
            <input name={name} type={type} defaultValue={value}/>
        </label>
    );
}

export default AddAndEditInput