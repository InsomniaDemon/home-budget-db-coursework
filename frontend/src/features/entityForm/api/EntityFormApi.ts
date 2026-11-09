import type {Row} from "@/entities/schema";
import {ApiError, toApiError} from "@/shared/apiError";

const BASE_URL = "http://localhost:8080/api";

export async function addOrEditRow(row: Row, jwt: string, logout: () => void, path: string, method: string, id?: number): Promise<void> {
    const url = `${BASE_URL}${path}${id ? "/" + id : ""}`

    const response = await fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwt}`
        },
        body: JSON.stringify(row)
    })

    if (response.status === 401) {
        logout()
    } else if (response.status === 403) {
        throw new ApiError(403, "ACCESS_DENIED", "You don't have permission to perform this action")
    } else if (!response.ok) {
        throw await toApiError(response)
    }
}
