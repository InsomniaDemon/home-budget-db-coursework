export type Row = Record<string, unknown>
export type FieldType = "text" | "number" | "datetime"

export interface FieldConfig {
    name: string
    type: FieldType
    inForm?: boolean
}

export interface EntityConfig {
    path: string
    fields: FieldConfig[]
    canAdd: boolean
    canEdit: boolean
    deleteWithOps?: boolean
}

export const articles: EntityConfig = {
    path: "/articles", canAdd: true, canEdit: true, deleteWithOps: true,
    fields: [
        {name: "id", type: "number", inForm: false},
        {name: "name", type: "text"},
    ],
}

export const operations: EntityConfig = {
    path: "/op", canAdd: true, canEdit: true,
    fields: [
        {name: "id", type: "number", inForm: false},
        {name: "articleId", type: "number"},
        {name: "debit", type: "number"},
        {name: "credit", type: "number"},
        {name: "createDate", type: "datetime"},
        {name: "balanceId", type: "number", inForm: false},
    ],
}

export const balances: EntityConfig = {
    path: "/balances", canAdd: false, canEdit: false,
    fields: ["id", "createDate", "debit", "credit", "amount"]
        .map(n => ({name: n, label: n, type: "number" as const, inForm: false})),
}