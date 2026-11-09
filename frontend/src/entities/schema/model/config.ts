export type Row = Record<string, unknown>
export type FieldType = "text" | "number" | "datetime"

export interface FieldConfig {
    name: string
    label: string
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
        {name: "id", label: "id",type: "number", inForm: false},
        {name: "name", label: "name",type: "text"},
    ],
}

export const operations: EntityConfig = {
    path: "/op", canAdd: true, canEdit: true,
    fields: [
        {name: "id", label: "id",type: "number", inForm: false},
        {name: "articleId", label: "articleId",type: "number"},
        {name: "debit", label: "debit",type: "number"},
        {name: "credit", label: "credit",type: "number"},
        {name: "createDate", label: "createDate UTC",type: "datetime"},
        {name: "balanceId", label: "balanceId",type: "number", inForm: false},
    ],
}

export const balances: EntityConfig = {
    path: "/balances", canAdd: false, canEdit: false,
    fields: [
        {name: "id", label: "id",type: "number", inForm: false},
        {name: "createDate", label: "createDate UTC",type: "datetime", inForm: false},
        {name: "debit", label: "debit",type: "number", inForm: false},
        {name: "credit", label: "credit",type: "number", inForm: false},
        {name: "amount", label: "amount",type: "number", inForm: false},
    ],
}

export interface ReportConfig {
    path: string
    fields: FieldConfig[]
    dateRequired: boolean
}

export const percentage: ReportConfig = {
    path: "/percentage", dateRequired: false,
    fields: [
        {name: "articleId", label: "articleId",type: "number"},
        {name: "debit", label: "debit",type: "number"},
        {name: "credit", label: "credit",type: "number"},
        {name: "amount", label: "amount",type: "number"},
    ],
}

export const incomeExpense: ReportConfig = {
    path: "/i-e-dynamics", dateRequired: true,
    fields: [
        {name: "date", label: "date",type: "datetime"},
        {name: "debit", label: "debit",type: "number"},
        {name: "credit", label: "credit",type: "number"},
    ],
}

export const profit: ReportConfig = {
    path: "/profit", dateRequired: true,
    fields: [
        {name: "date", label: "date",type: "datetime"},
        {name: "netProfit", label: "netProfit",type: "number"},
    ],
}