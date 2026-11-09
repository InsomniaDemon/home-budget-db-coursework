import {GridView} from "@/widgets/gridview";
import {articles} from "@/entities/schema";


export function ReportsPage() {
    return (
        <div>
            <GridView config={articles}/>
        </div>
    );
}

export default ReportsPage