import {GridView} from "@/widgets/gridview";


export function ArticlesPage() {
    const headers: string[] = ["id", "name"]
    const rows: object[] = [
        {id: 1, name: "salary"},
        {id: 2, name: "haircut"},
    ]

    return (
        <div>
            <GridView headers={headers} rows={rows}/>
        </div>
    );
}

export default ArticlesPage