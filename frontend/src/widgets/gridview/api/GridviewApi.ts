import type {Row} from "@/entities/schema";

const BASE_URL = "http://localhost:8080/api";

export async function getObjectTable(jwt: string, logout: () => void, path: string): Promise<Row[]> {
    const response = await fetch(`${BASE_URL}${path}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${jwt}`,
        },
    })

    if (response.status === 401) {
        logout()
    } else if (!response.ok) {
        throw new Error(`Failed to fetch table: ${response.status}`)
    }

    return await response.json();
}