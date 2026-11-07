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
    } else if (!response.ok) {
        throw new Error(`Failed to delete ${id}: ${response.status}`)
    }
}
