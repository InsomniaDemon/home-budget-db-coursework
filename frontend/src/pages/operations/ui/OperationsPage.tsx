import styles from "./OperationsPage.module.scss"
import {GridView} from "@/widgets/gridview";
import {useAuth} from "@/entities/auth";
import {useEffect, useState} from "react";
import {getObjectTable} from "@/pages/operations/api/OperationsApi.ts";

export function OperationsPage() {
    const headers: string[] = ["id", "article_id", "debit", "credit", "create_date", "balance_id"]
        /*const rows: object[] = [
        {id: 1, article_id: 1, debit: 500, credit: 0, create_date: "03.11.2026", balance_id: null},
        {id: 2, article_id: 1, debit: 0, credit: -100, create_date: "03.11.2026", balance_id: null},
    ]*/

    const [rows, setRows] = useState<object[]>([])

    const {jwt, logout} = useAuth();

    useEffect(() => {
        getObjectTable(jwt, logout)
            .then(setRows)
            .catch(console.error)
    }, [jwt, logout])

    return (
        <div className={styles.operations}>
            <GridView headers={headers} rows={rows}/>
        </div>
    );
}

export default OperationsPage