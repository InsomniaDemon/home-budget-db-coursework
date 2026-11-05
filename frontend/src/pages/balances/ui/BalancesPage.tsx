import {GridView} from "@/widgets/gridview";


export function BalancesPage() {
    const headers: string[] = ["id", "create_date", "debit", "credit", "amount"]

    return (
        <div>
            <GridView headers={headers} path="/balances"/>
        </div>
    );
}

export default BalancesPage