import { PageLink } from "@/shared/pageLink"
import styles from "./Header.module.scss"
import {useEffect} from "react";
import {useAuth} from "@/entities/auth";
import {useNavigate} from "react-router-dom";
import logoutImg from "@/assets/images/logout.png"


export function Header() {
    const {jwt, logout} = useAuth()
    const navigate = useNavigate()

    useEffect(() => {
        if (!jwt) {
            navigate("/auth")
        }
    },[jwt, navigate])

    return (
        <div className="container">
            <div className={styles.header}>
                <div className={styles.title}>
                    <p className={styles.home}>Home</p>
                    <p className={styles.budget}>Budget</p>
                </div>
                {jwt && <PageLink page="/operations" buttonText="Operations"/>}
                {jwt && <PageLink page="/articles" buttonText="Articles"/>}
                {jwt && <PageLink page="/balances" buttonText="Balances"/>}
                {jwt && <PageLink page="/reports" buttonText="Reports"/>}
                {jwt &&
                    <button className={styles.logout} onClick={logout}>
                        <img src={logoutImg} alt="logout img"/>
                    </button>}
            </div>
        </div>
    );
}

export default Header