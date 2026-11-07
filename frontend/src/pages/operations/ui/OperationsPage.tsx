import styles from "./OperationsPage.module.scss"
import {GridView} from "@/widgets/gridview";
import {operations} from "@/entities/schema";

export function OperationsPage() {

    return (
        <div className={styles.operations}>
            <GridView config={operations}/>
        </div>
    );
}

export default OperationsPage