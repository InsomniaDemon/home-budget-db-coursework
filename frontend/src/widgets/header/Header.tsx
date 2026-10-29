import PageLink from "../../shared/page link/PageLink.tsx";
import styles from "./Header.module.scss"


export function Header() {
    return (
        <div className="container">
            <div className={styles.header}>
                <PageLink page="/operations" buttonText="Operations"/>
                <PageLink page="/articles" buttonText="Articles"/>
                <PageLink page="/balances" buttonText="Balances"/>
            </div>
        </div>
    );
}

export default Header