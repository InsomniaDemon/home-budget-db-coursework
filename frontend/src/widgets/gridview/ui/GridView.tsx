import styles from "./GridView.module.scss"
import {useEffect, useState} from "react";
import {getObjectTable} from "@/widgets/gridview";
import {useAuth} from "@/entities/auth";

export function GridView({headers, path}: {headers: string[], path: string}) {
    const [rows, setRows] = useState<object[]>([])
    const {jwt, logout} = useAuth()

    useEffect(() => {
        getObjectTable(jwt, logout, path)
            .then(setRows)
            .catch(console.error)
    }, [jwt, logout, path])

    return (
        <div className={styles.gridview}>
            <div className={styles.row}>
                {headers.map((header) => <span key={header} className={`${styles.cell} ${styles.header}`}>{header}</span>)}
            </div>
            {rows.map((row, index) =>
                <div key={index} className={styles.row}>
                    {Object.values(row).map((value, i) => (
                        <span key={i} className={styles.cell}>{value !== null ? value : "null"}</span>
                    ))}
                </div>)}
        </div>
    );
}

export default GridView