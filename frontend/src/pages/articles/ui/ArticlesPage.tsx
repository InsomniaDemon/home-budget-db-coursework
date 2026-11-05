import {GridView} from "@/widgets/gridview";
import {ArticleAddPopup} from "@/features/articleAddPopup";
import {useState} from "react";


export function ArticlesPage() {
    const headers: string[] = ["id", "name"]
    const [isAddPopupOpened, setIsAddPopupOpened] = useState<boolean>(false)
    const [counter, setCounter] = useState<number>(0)

    return (
        <div>
            {isAddPopupOpened && <ArticleAddPopup close={() => setIsAddPopupOpened(false)} updateCounter={() => setCounter(counter + 1)}/>}
            <GridView headers={headers} path="/articles" isEditable={true} openAdd={() => setIsAddPopupOpened(true)} counter={counter}/>
        </div>
    );
}

export default ArticlesPage