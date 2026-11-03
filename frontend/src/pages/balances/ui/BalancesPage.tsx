import {GridView} from "@/widgets/gridview";


export function BalancesPage() {
    const headers: string[] = ["id", "create_date", "debit", "credit", "amount"]
    const rows: object[] = [
        {id: 1, create_date: "03.11.2026", debit: 500, credit: 0, amount: 500},
        {id: 2, create_date: "03.11.2026", debit: 0, credit: -100, amount: -100},
    ]

    return (
        <div>
            <GridView headers={headers} rows={rows}/>
        </div>
    );
}

export default BalancesPage