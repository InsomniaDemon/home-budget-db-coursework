import {ApiError, toApiError} from "@/shared/apiError";

const BASE_URL = "http://localhost:8080/api";

export async function deleteRow(id: number, jwt: string, logout: () => void, path: string): Promise<void> {
    const response = await fetch(`${BASE_URL}${path}${"/"+id}`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json',
                    'Authorization': `Bearer ${jwt}`
        }
    })

    if (response.status === 401) {
        logout()
    } else if (response.status === 403) {
        throw new ApiError(403, "ACCESS_DENIED", "You don't have permission to perform this action")
    } else if (!response.ok) {
        throw await toApiError(response)
    }
}
