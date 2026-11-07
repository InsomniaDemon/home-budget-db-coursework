import styles from "./GridView.module.scss"
import {useEffect, useState} from "react";
import {getObjectTable} from "@/widgets/gridview";
import {useAuth} from "@/entities/auth";
import edit from "@/assets/images/edit.png"
import trashCan from "@/assets/images/trash-can.png"
import plus from "@/assets/images/plus.png"
import {DeletePopup} from "@/features/deletePopup";

export function GridView({headers, path, isEditable, openAdd, openEdit, counter}: {headers: string[], path: string, isEditable: boolean, openAdd?: () => void, openEdit?: () => void, counter?: number}) {
    const [rows, setRows] = useState<object[]>([])
    const [deleteId, setDeleteId] = useState<number | null>(null)
    const {jwt, logout} = useAuth()

    useEffect(() => {
        getObjectTable(jwt, logout, path)
            .then(setRows)
            .catch(console.error)
    }, [jwt, logout, path, counter, deleteId])

    return (
        <div className={styles.gridview}>
            {deleteId && <DeletePopup close={() => setDeleteId(null)} deleteId={deleteId} path={path}/>}
            <div className={styles.table}>
                <div className={styles.row}>
                    {headers.map((header) => <span key={header} className={`${styles.cell} ${styles.header}`}>{header}</span>)}
                </div>
                {rows.map((row, index) => <div className={styles.rowAndButtons} key={"row-and-button-" + index}>
                        <div key={"row-" + index} className={styles.row}>
                            {Object.values(row).map((value, i) => (
                                <span key={index + "-" + i} className={styles.cell}>{value !== null ? value : "null"}</span>
                            ))}
                        </div>
                        <div className={styles.buttons} onClick={openEdit}>
                            {
                                isEditable &&
                                <button key={"edit-" + index}>
                                    <img src={edit} alt="Edit img"/>
                                </button>
                            }
                            <button key={"delete-" + index} onClick={() => setDeleteId?.(row["id"])}>
                                <img src={trashCan} alt="Trash can img"/>
                            </button>
                        </div>
                </div>
                )}
            </div>
            <button className={styles.plus} onClick={openAdd}>
                <img src={plus} alt="Plus img"/>
            </button>
        </div>
    );
}

export default GridView