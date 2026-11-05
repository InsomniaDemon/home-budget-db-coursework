import styles from "./OperationsPage.module.scss"
import {GridView} from "@/widgets/gridview";

export function OperationsPage() {
    const headers: string[] = ["id", "article_id", "debit", "credit", "create_date", "balance_id"]

    return (
        <div className={styles.operations}>
            <GridView headers={headers} path={"/op"}/>
        </div>
    );
}

export default OperationsPage