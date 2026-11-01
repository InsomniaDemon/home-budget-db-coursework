import { PageLink } from "@/shared/pageLink"
import styles from "./Header.module.scss"
import {useEffect} from "react";
import {useAuth} from "@/entities/auth";
import {useNavigate} from "react-router-dom";


export function Header() {
    const {jwt} = useAuth()
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
                <PageLink page="/operations" buttonText="OperationsPage"/>
                <PageLink page="/articles" buttonText="ArticlesPage"/>
                <PageLink page="/balances" buttonText="BalancesPage"/>
            </div>
        </div>
    );
}

export default Header