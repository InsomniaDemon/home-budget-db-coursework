import styles from "./ReportGridView.module.scss"
import {useEffect, useState} from "react";
import {useAuth} from "@/entities/auth";
import type {ReportConfig, Row} from "@/entities/schema"
import {AddAndEditInput} from "@/shared/addAndEditInput";
import {SubmitButton} from "@/shared/submitButton";

export function ReportsGridView({config}: {config: ReportConfig}) {
    const [rows, setRows] = useState<Row[]>([])
    const {jwt, logout} = useAuth()

    /*useEffect(() => {
        getReportTable(jwt, logout, config.path)
            .then(setRows)
            .catch(console.error)
    }, [jwt, logout, config.path])*/

    return (
        <div className={styles.report}>
            <form className={styles.query}>
                <AddAndEditInput label="dateFrom" name="date-from" type="datetime"/>
                <AddAndEditInput label="dateTo" name="date-to" type="datetime"/>
                <AddAndEditInput label="articleIds" name="article-ids" type="string"/>
                <SubmitButton text={"Browse report"}/>
            </form>

            <div className={styles.reportTable}>
                <div className={styles.row}>
                    {config.fields.map((field) =>
                        <div key={field.name} className={`${styles.cell} ${styles.header}`}>
                            <span>{field.label}</span>
                            <span className={styles.type}>${field.type}</span>
                        </div>)}
                </div>
                {rows.map((row, index) =>
                    <div className={styles.row} key={index}>
                        {config.fields.map(f => (
                            <span key={f.name} className={styles.cell}>{row[f.name] == null ? "null" : String(row[f.name])}</span>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}

export default ReportsGridView