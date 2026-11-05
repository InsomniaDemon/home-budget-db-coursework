import {GridView} from "@/widgets/gridview";


export function ArticlesPage() {
    const headers: string[] = ["id", "name"]

    return (
        <div>
            <GridView headers={headers} path="/articles" isEditable={true}/>
        </div>
    );
}

export default ArticlesPage