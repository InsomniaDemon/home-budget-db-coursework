import {GridView} from "@/widgets/gridview";
import {articles} from "@/entities/schema";


export function ArticlesPage() {
    return (
        <div>
            <GridView config={articles}/>
        </div>
    );
}

export default ArticlesPage