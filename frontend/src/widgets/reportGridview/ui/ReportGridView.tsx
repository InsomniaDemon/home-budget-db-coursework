import styles from "./ReportGridView.module.scss"
import {useEffect, useState} from "react";
import {useAuth} from "@/entities/auth";
import type {ReportConfig, Row} from "@/entities/schema"
import {AddAndEditInput} from "@/shared/addAndEditInput";
import {SubmitButton} from "@/shared/submitButton";
import {getReportTable} from "@/widgets/reportGridview";

export function ReportsGridView({config}: {config: ReportConfig}) {
    const [rows, setRows] = useState<Row[]>([])
    const {jwt, logout} = useAuth()

    useEffect(() => {
        const path = `${config.path}?${config.dateRequired ? "dateFrom=2026-01-01T00:00&dateTo=2026-12-31T23:59&" : ""}articleIds=1&articleIds=2`;
        getReportTable(jwt, logout, path)
            .then(setRows)
            .catch(console.error)
    }, [jwt, logout, config.path, config.dateRequired])

    const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
        e.preventDefault()

        const formData = new FormData(e.currentTarget)

        let path: string = config.path + "?"

        if (config.dateRequired) {
            const dateFrom = formData.get("date-from") as string
            const dateTo = formData.get("date-to") as string

            path += "dateFrom=" + dateFrom + "&dateTo=" + dateTo
        }

        const articleIdsString = formData.get("article-ids") as string

        if (!/^[\d\s]+$/.test(articleIdsString)) {
            console.error("Invalid article IDs");
            return;
        }

        for(const articleId of articleIdsString.split(" ")) {
            path += "&articleIds=" + articleId
        }

        try {
            getReportTable(jwt, logout, path)
                .then(setRows)
        } catch (err) {
            console.log(err)
        }
    }

    return (
        <div className={styles.report}>
            <form className={styles.query} onSubmit={handleSubmit}>
                {config.dateRequired && <AddAndEditInput label="dateFrom" name="date-from" type="datetime-local"/>}
                {config.dateRequired && <AddAndEditInput label="dateTo" name="date-to" type="datetime-local"/>}
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