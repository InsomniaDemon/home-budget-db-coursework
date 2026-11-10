import {GridView} from "@/widgets/gridview";
import {articles} from "@/entities/schema";


export function ArticlesPage() {
    return (
            <GridView config={articles}/>
    );
}

export default ArticlesPage