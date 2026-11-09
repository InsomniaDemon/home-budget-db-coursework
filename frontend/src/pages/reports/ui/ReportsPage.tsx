import styles from "./ReportsPage.module.scss";
import {useState} from "react";
import ReportGridView from "@/widgets/reportGridview/ui/ReportGridView.tsx";
import {incomeExpense, percentage, profit} from "@/entities/schema";


export function ReportsPage() {
    const [reportShown, setReportShown] = useState<"article-percentage" | "income-expense-dynamics" | "net-profit-dynamics">("article-percentage")

    return (
        <div className={styles.reportsPage}>
            <div className={styles.options}>
                <label className={styles.chosenForm} >
                    <input type="radio" name="page" checked={reportShown === "article-percentage"} onChange={() => setReportShown("article-percentage")}></input>
                    <span>Article percentage</span>
                </label>
                <label className={styles.chosenForm}>
                    <input type="radio" name="page" checked={reportShown === "income-expense-dynamics"} onChange={() => setReportShown("income-expense-dynamics")}></input>
                    <span>Income and expense dynamics</span>
                </label>
                <label className={styles.chosenForm}>
                    <input type="radio" name="page" checked={reportShown === "net-profit-dynamics"} onChange={() => setReportShown("net-profit-dynamics")}></input>
                    <span>Net profit dynamics</span>
                </label>
            </div>
            {reportShown === "article-percentage" && ReportGridView({config: percentage})}
            {reportShown === "income-expense-dynamics" && ReportGridView({config: incomeExpense})}
            {reportShown === "net-profit-dynamics" && ReportGridView({config: profit})}
        </div>
    );
}

export default ReportsPage