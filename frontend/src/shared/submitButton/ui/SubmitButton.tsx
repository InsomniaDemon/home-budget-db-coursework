import styles from "./SubmitButton.module.scss"

export function SubmitButton({text}: {text: string}) {
    return (
        <button className={styles.submitButton}>{text}</button>
    );
}

export default SubmitButton