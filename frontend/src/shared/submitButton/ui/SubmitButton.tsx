import styles from "./SubmitButton.module.scss"

export function OperationsPage({text}: {text: string}) {
    return (
        <button className={styles.submitButton}>{text}</button>
    );
}

export default OperationsPage