import styles from "./EntityFormPopup.module.scss"
import {SubmitButton} from "@/shared/submitButton";
import {AddAndEditInput} from "@/shared/addAndEditInput";
import cross from "@/assets/images/cross.png"
import {useAuth} from "@/entities/auth";
import type {EntityConfig, FieldConfig, Row} from "@/entities/schema";
import {addOrEditRow} from "@/features/entityForm/api/EntityFormApi.ts";
import {useState} from "react";
import {ApiError} from "@/shared/apiError";
import {ErrorPopup} from "@/shared/errorPopup";

type FormErrors = {
    name?: string
    articleId?: string
    balanceId?: string
    createDate?: string
    debit?: string
    credit?: string
}

const errorsForPopup = ["UPDATING_INCLUDED_OPERATION", "DEBIT_AND_CREDIT_EQUALS_ZERO", "ACCESS_DENIED"]

const toInputValue = (f: FieldConfig, v: unknown) =>
    v == null ? "" : f.type === "datetime" ? String(v).replace(" ", "T").slice(0, 19) : String(v)

export function EntityFormPopup({config, row, close, onSaved}:
                                {config: EntityConfig, row?: Row, close: () => void, onSaved: () => void}) {
    const {jwt, logout} = useAuth();
    const fields = config.fields.filter(f => f.inForm !== false)
    const [errors, setErrors] = useState<FormErrors>({})
    const [errorText, setErrorText] = useState<string | null>(null)

    const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
        e.preventDefault()

        setErrors({})
        const newErrors: FormErrors = {}
        let toAbort: boolean = false

        const formData = new FormData(e.currentTarget)
        const body: Row = {}
        for (const f of fields) {
            const raw = formData.get(f.name) as string

            if (raw.length === 0) {
                newErrors[f.name] = "Empty " + f.name
                toAbort = true
            } else if (f.name === "name" && raw.length > 20) {
                newErrors[f.name] = "Too many characters"
                toAbort = true
            }

            body[f.name] = f.type === "number" ? Number(raw) : raw
        }

        if (toAbort) {
            setErrors(newErrors)
            return
        }

        try {
            await addOrEditRow(body, jwt, logout, config.path, row ? "PUT" : "POST", row ? Number(row["id"]) : null)
            onSaved()
            close()
        } catch (err) {
            if (err instanceof ApiError) {
                if (err.code === "INVALID_ARTICLE_ID") {
                    setErrors({articleId: err.message})
                } else if (err.code === "ARTICLE_ALREADY_EXIST") {
                    setErrors({name: err.message})
                } else if (errorsForPopup.includes(err.code)) {
                    setErrorText(err.message)
                }
            } else {
                setErrors(
                    {name: "unknown",
                    articleId: "unknown",
                    balanceId: "unknown",
                    createDate: "unknown",
                    debit: "unknown",
                    credit: "unknown"}
                )
            }
        }
    }

    return (
        <div className={styles.popup}>
            <div className={styles.bg}/>

            {errorText &&
                <div className={styles.card}>
                    <button className={styles.cross} onClick={close}>
                        <img src={cross} alt="cross image"/>
                    </button>
                    <ErrorPopup text={errorText}/>

                </div>
            }
            {!errorText &&
                <form className={styles.card} onSubmit={handleSubmit}>
                    <button className={styles.cross} onClick={close}>
                        <img src={cross} alt="cross image"/>
                    </button>
                    {fields.length !== 0 &&
                        <div className={styles.inputs}>
                            {fields.map(field =>
                                <AddAndEditInput key={field.name} label={field.label} name={field.name} type={field.type === "datetime" ? "datetime-local" : field.type} value={row ? toInputValue(field, row[field.name]) : ""} error={errors[field.name]}/>
                            )}
                        </div>
                    }
                    <SubmitButton text={row ? "edit" : "add"}/>
                </form>
            }
        </div>
    );
}

export default EntityFormPopup