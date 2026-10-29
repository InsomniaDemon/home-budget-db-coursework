import {NavLink} from "react-router-dom";
import styles from "./PageLink.module.scss"

function PageLink({page, buttonText}: {page: string, buttonText: string}) {
    return (
        <NavLink to={page} className={styles.pageLink}>
            <p>{buttonText}</p>
        </NavLink>
    );
}

export default PageLink