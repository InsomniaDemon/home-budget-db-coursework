import styles from "./GridView.module.scss"
import {useEffect, useState} from "react";
import {getObjectTable} from "@/widgets/gridview";
import {useAuth} from "@/entities/auth";
import edit from "@/assets/images/edit.png"
import trashCan from "@/assets/images/trash-can.png"
import plus from "@/assets/images/plus.png"
import {DeletePopup} from "@/features/deletePopup";
import type {EntityConfig, Row} from "@/entities/schema"
import {EntityFormPopup} from "@/features/entityForm";

export function GridView({config}: {config: EntityConfig}) {
    const [rows, setRows] = useState<Row[]>([])
    const [deleteId, setDeleteId] = useState<number | null>(null)
    const [reloadKey, setReloadKey] = useState(0)
    const reload = () => setReloadKey(k => k + 1)
    const [form, setForm] = useState<{row?: Row} | null>(null)
    const {jwt, logout} = useAuth()

    useEffect(() => {
        getObjectTable(jwt, logout, config.path)
            .then(setRows)
            .catch(console.error)
    }, [jwt, logout, config.path, reloadKey])

    return (
        <div className={styles.gridview}>
            {deleteId && <DeletePopup close={() => { setDeleteId(null); reload() }} deleteId={deleteId} path={config.path}/>}
            {form && <EntityFormPopup config={config} row={form.row} close={() => setForm(null)} onSaved={reload}/>}
            <div className={styles.table}>
                <div className={styles.row}>
                    {config.fields.map((field) =>
                        <div key={field.name} className={`${styles.cell} ${styles.header}`}>
                            <span>{field.label}</span>
                            <span className={styles.type}>${field.type}</span>
                        </div>)}
                </div>
                {rows.map((row, index) => <div className={styles.rowAndButtons} key={"row-and-button-" + index}>
                        <div className={styles.row}>
                            {config.fields.map(f => (
                                <span key={f.name} className={styles.cell}>{row[f.name] == null ? "null" : String(row[f.name])}</span>
                            ))}
                        </div>
                        <div className={styles.buttons}>
                            {config.canEdit &&
                                <button key={"edit-" + index} onClick={() => setForm({row})}>
                                    <img src={edit} alt="Edit img"/>
                                </button>}
                            <button key={"delete-" + index} onClick={() => setDeleteId?.(row.id as number)}>
                                <img src={trashCan} alt="Trash can img"/>
                            </button>
                        </div>
                </div>
                )}
            </div>
            <button className={styles.plus} onClick={() => setForm({})}>
                <img src={plus} alt="Plus img"/>
            </button>
        </div>
    );
}

export default GridView