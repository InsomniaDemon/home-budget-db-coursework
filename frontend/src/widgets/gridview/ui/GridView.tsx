import styles from "./GridView.module.scss"

export function GridView({headers, rows}: {headers: string[], rows: object[]}) {
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