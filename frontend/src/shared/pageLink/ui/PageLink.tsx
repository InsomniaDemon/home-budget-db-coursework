import {NavLink} from "react-router-dom";
import styles from "./PageLink.module.scss"

function PageLink({page, buttonText}: {page: string, buttonText: string}) {
    const linkClassName = ({ isActive }: { isActive: boolean }) =>
        isActive ? `${styles.pageLink} ${styles.active}` : styles.pageLink

    return (
        <NavLink to={page} className={linkClassName}>
            <p>{buttonText}</p>
        </NavLink>
    );
}

export default PageLink