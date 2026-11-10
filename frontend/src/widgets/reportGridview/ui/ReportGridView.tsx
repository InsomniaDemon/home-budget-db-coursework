import styles from "./ReportGridView.module.scss"
import {useEffect, useState} from "react";
import {useAuth} from "@/entities/auth";
import type {ReportConfig, Row} from "@/entities/schema"
import {AddAndEditInput} from "@/shared/addAndEditInput";
import {SubmitButton} from "@/shared/submitButton";
import {getReportTable} from "@/widgets/reportGridview";

type FormErrors = {
    dateFrom?: string
    dateTo?: string
    articleIds?: string
}

export function ReportsGridView({config}: {config: ReportConfig}) {
    const [rows, setRows] = useState<Row[]>([])
    const [errors, setErrors] = useState<FormErrors>({})
    const {jwt, logout} = useAuth()

    useEffect(() => {
        const path = `${config.path}?${config.dateRequired ? "dateFrom=2026-01-01T00:00&dateTo=2026-12-31T23:59&" : ""}articleIds=1&articleIds=2`;
        getReportTable(jwt, logout, path)
            .then(setRows)
            .catch(console.error)
    }, [jwt, logout, config.path, config.dateRequired])

    const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
        e.preventDefault()

        setErrors({})
        const newErrors: FormErrors = {}
        let toAbort: boolean = false

        const formData = new FormData(e.currentTarget)

        let path: string = config.path + "?"

        if (config.dateRequired) {
            const dateFrom = formData.get("date-from") as string
            const dateTo = formData.get("date-to") as string

            if (dateFrom.length === 0) {
                newErrors.dateFrom = "Empty dateFrom"
                toAbort = true
            }
            if (dateTo.length === 0) {
                newErrors.dateTo = "Empty dateTo"
                toAbort = true
            }

            path += "dateFrom=" + dateFrom + "&dateTo=" + dateTo
        }

        const articleIdsString = formData.get("article-ids") as string
        if (articleIdsString.length === 0 || /^\s+$/.test(articleIdsString)) {
            newErrors.articleIds = "Empty articleIds"
            toAbort = true;
        } else if (!/^[\d\s]+$/.test(articleIdsString)) {
            newErrors.articleIds = "Use only numbers and spaces"
            toAbort = true;
        }

        for(const articleId of articleIdsString.split(" ")) {
            path += "&articleIds=" + articleId
        }

        if (toAbort) {
            setErrors(newErrors)
            return
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
                {config.dateRequired && <AddAndEditInput label="dateFrom" name="date-from" type="datetime-local" error={errors.dateFrom}/>}
                {config.dateRequired && <AddAndEditInput label="dateTo" name="date-to" type="datetime-local" error={errors.dateTo}/>}
                <AddAndEditInput label="articleIds" name="article-ids" type="string" error={errors.articleIds}/>
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