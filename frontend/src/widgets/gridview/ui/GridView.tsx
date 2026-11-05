import styles from "./GridView.module.scss"
import {useEffect, useState} from "react";
import {getObjectTable} from "@/widgets/gridview";
import {useAuth} from "@/entities/auth";
import edit from "@/assets/images/edit.png"
import trashCan from "@/assets/images/trash-can.png"
import plus from "@/assets/images/plus.png"

export function GridView({headers, path, isEditable}: {headers: string[], path: string, isEditable: boolean}) {
    const [rows, setRows] = useState<object[]>([])
    const {jwt, logout} = useAuth()

    useEffect(() => {
        getObjectTable(jwt, logout, path)
            .then(setRows)
            .catch(console.error)
    }, [jwt, logout, path])

    return (
        <div className={styles.gridview}>
            <div className={styles.table}>
                <div className={styles.row}>
                    {headers.map((header) => <span key={header} className={`${styles.cell} ${styles.header}`}>{header}</span>)}
                </div>
                {rows.map((row, index) =>
                    <div className={styles.rowAndButtons}>
                        <div key={index} className={styles.row}>
                            {Object.values(row).map((value, i) => (
                                <span key={i} className={styles.cell}>{value !== null ? value : "null"}</span>
                            ))}
                        </div>
                        <div className={styles.buttons}>
                            {
                                isEditable &&
                                <button key={index}>
                                    <img src={edit} alt="Edit img"/>
                                </button>
                            }
                            <button key={index}>
                                <img src={trashCan} alt="Trash can img"/>
                            </button>
                        </div>
                    </div>
                )}
            </div>
            <button className={styles.plus}>
                <img src={plus} alt="Plus img"/>
            </button>
        </div>
    );
}

export default GridView