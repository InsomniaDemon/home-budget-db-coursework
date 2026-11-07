import {GridView} from "@/widgets/gridview";
import {balances} from "@/entities/schema";


export function BalancesPage() {

    return (
        <div>
            <GridView config={balances}/>
        </div>
    );
}

export default BalancesPage