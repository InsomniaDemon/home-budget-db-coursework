import styles from "./EntityFormPopup.module.scss"
import {SubmitButton} from "@/shared/submitButton";
import {AddAndEditInput} from "@/shared/addAndEditInput";
import cross from "@/assets/images/cross.png"
import {useAuth} from "@/entities/auth";
import type {EntityConfig, FieldConfig, Row} from "@/entities/schema";
import {addOrEditRow} from "@/features/entityForm/api/EntityFormApi.ts";

const toInputValue = (f: FieldConfig, v: unknown) =>
    v == null ? "" : f.type === "datetime" ? String(v).replace(" ", "T").slice(0, 19) : String(v)

export function EntityFormPopup({config, row, close, onSaved}:
                                {config: EntityConfig, row?: Row, close: () => void, onSaved: () => void}) {
    const {jwt, logout} = useAuth();
    const fields = config.fields.filter(f => f.inForm !== false)

    const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
        e.preventDefault()

        const formData = new FormData(e.currentTarget)
        const body: Row = {}
        for (const f of fields) {
            const raw = formData.get(f.name) as string
            body[f.name] = f.type === "number" ? Number(raw) : raw
        }

        try {
            await addOrEditRow(body, jwt, logout, config.path, row ? "PUT" : "POST", row ? Number(row["id"]) : null)
            onSaved()
            close()
        } catch (err) {
            console.log(err)
        }
    }

    return (
        <div className={styles.popup}>
            <div className={styles.bg}/>
            <form className={styles.card} onSubmit={handleSubmit}>
                <button className={styles.cross} onClick={close}>
                    <img src={cross} alt="cross image"/>
                </button>
                {fields.length !== 0 &&
                    <div className={styles.inputs}>
                        {fields.map(field =>
                            <AddAndEditInput key={field.name} label={field.label} name={field.name} type={field.type === "datetime" ? "datetime-local" : field.type} value={row ? toInputValue(field, row[field.name]) : ""}/>
                        )}
                    </div>
                }
                <SubmitButton text={row ? "edit" : "add"}/>
            </form>
        </div>
    );
}

export default EntityFormPopup