import styles from "./AddAndEditInput.module.scss"

export function AddAndEditInput({label, name, type = "text", value = ""}: {label: string, name: string, type?: string, value?: string | number}) {

    return (
        <label className={styles.addAndEditInput}>
            <span>{label}:</span>
            <input name={name} type={type} defaultValue={value}/>
        </label>
    );
}

export default AddAndEditInput