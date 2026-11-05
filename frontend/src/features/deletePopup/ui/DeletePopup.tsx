import styles from "./DeletePopup.module.scss"
import cross from "@/assets/images/cross.png"
import {useAuth} from "@/entities/auth";
import {deleteRow} from "@/features/deletePopup";

export function DeletePopup({close, deleteId, path}: {close: () => void, deleteId: number, path: string}) {
    const {jwt, logout} = useAuth();

    const handleDelete = async (path: string) => {
        try {
            await deleteRow(deleteId, jwt, logout, path)
            close()
        } catch (err) {
            console.log(err)
        }
    }

    return (
        <div className={styles.popup}>
            <div className={styles.bg}/>
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
        </div>
    );
}

export default DeletePopup