import styles from "./ErrorPopup.module.scss"

export function ErrorPopup({text}: {text: string}) {

    return (
        <p className={styles.errorText}>{text}</p>
    )
}

export default ErrorPopup