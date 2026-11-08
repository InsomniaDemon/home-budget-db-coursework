import {toApiError} from "@/shared/apiError";

const BASE_URL = "http://localhost:8080/api";

export async function getDoesOwnerExist(): Promise<boolean> {
    const path = `/reg/owner`

    const response = await fetch(`${BASE_URL}${path}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json'},
    })

    if (!response.ok) {
        throw await toApiError(response)
    }

    return await response.json();
}

export async function registerNewOwner(username: string, password: string): Promise<string> {
    const path = `/reg/owner`

    const response = await fetch(`${BASE_URL}${path}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify( {username, password} ),
    })

    if (!response.ok) {
        throw await toApiError(response)
    }

    return await response.text();
}

export async function registerNewUser(username: string, password: string, role: string, ownersPassword: string): Promise<string> {
    const path = `/reg`

    const response = await fetch(`${BASE_URL}${path}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify({username, password, role, ownersPassword}),
    })

    if (!response.ok) {
        throw await toApiError(response)
    }

    return await response.text();
}