import styles from "./ArticleAddPopup.module.scss"
import {SubmitButton} from "@/shared/submitButton";
import {AddAndEditInput} from "@/shared/addAndEditInput";
import cross from "@/assets/images/cross.png"
import {addArticle} from "@/features/ArticleAddPopup";
import {useAuth} from "@/entities/auth";

export function ArticleAddPopup({close, updateCounter}: {close: () => void, updateCounter: () => void}) {
    const {jwt, logout} = useAuth();

    const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
        e.preventDefault()

        const formData = new FormData(e.currentTarget)

        const name = formData.get("name") as string

        try {
            await addArticle(name, jwt, logout)
            updateCounter()
            close()
        } catch (err) {
            console.log(err)
        }
    }

    return (
        <div className={styles.popup}>
            <div className={styles.bg}/>
            <form className={styles.card} onSubmit={handleSubmit}>
                <button className={styles.cross} onClick={close}>
                    <img src={cross} alt="cross image"/>
                </button>
                <div className={styles.inputs}>
                    <AddAndEditInput label="name" name="name"/>
                </div>
                <SubmitButton text="add new article"/>
            </form>
        </div>
    );
}

export default ArticleAddPopup