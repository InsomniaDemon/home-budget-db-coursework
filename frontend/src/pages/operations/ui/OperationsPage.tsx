import {GridView} from "@/widgets/gridview";
import {operations} from "@/entities/schema";

export function OperationsPage() {

    return (
        <GridView config={operations}/>
    );
}

export default OperationsPage