import { PageLink } from "@/shared/pageLink"
import styles from "./Header.module.scss"


export function Header() {

    return (
        <div className="container">
            <div className={styles.header}>
                <div className={styles.title}>
                    <p className={styles.home}>Home</p>
                    <p className={styles.budget}>Budget</p>
                </div>
                <PageLink page="/operations" buttonText="Operations"/>
                <PageLink page="/articles" buttonText="Articles"/>
                <PageLink page="/balances" buttonText="Balances"/>
            </div>
        </div>
    );
}

export default Header