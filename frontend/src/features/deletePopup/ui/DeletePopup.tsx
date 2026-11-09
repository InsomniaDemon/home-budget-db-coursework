import styles from "./DeletePopup.module.scss"
import cross from "@/assets/images/cross.png"
import {useAuth} from "@/entities/auth";
import {deleteRow} from "@/features/deletePopup";
import {useState} from "react";
import {ApiError} from "@/shared/apiError";
import {ErrorPopup} from "@/shared/errorPopup";

const errorsForPopup = ["DELETING_INCLUDED_OPERATION", "ACCESS_DENIED"]

export function DeletePopup({close, deleteId, path}: {close: () => void, deleteId: number, path: string}) {
    const {jwt, logout} = useAuth();
    const [errorText, setErrorText] = useState<string | null>(null)

    const handleDelete = async (path: string) => {
        try {
            await deleteRow(deleteId, jwt, logout, path)
            close()
        } catch (err) {
            if (err instanceof ApiError) {
                if (errorsForPopup.includes(err.code)) {
                    setErrorText(err.message)
                }
            } else {
                setErrorText("unknown")
            }
        }
    }

    return (
        <div className={styles.popup}>
            <div className={styles.bg}/>
            {errorText &&
                <div className={styles.card}>
                    <button className={styles.cross} onClick={close}>
                        <img src={cross} alt="cross image"/>
                    </button>
                    <ErrorPopup text={errorText}/>
                </div>
            }
            {!errorText &&
                <div className={styles.card} >
                    <button className={styles.cross} onClick={close}>
                        <img src={cross} alt="cross image"/>
                    </button>
                    <span>Are you sure?</span>
                    <div className={styles.deleteButtons}>
                        <button onClick={() => handleDelete(path)}>
                            Delete
                        </button>
                        {path != "/op" &&
                            <button onClick={() => handleDelete(path + "/ops")}>
                                Delete with ops
                            </button>
                        }
                    </div>
                </div>
            }
        </div>
    );
}

export default DeletePopup