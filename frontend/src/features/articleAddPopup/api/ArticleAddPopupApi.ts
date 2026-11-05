const BASE_URL = "http://localhost:8080/api";

export async function addArticle(name: string, jwt: string, logout: () => void): Promise<void> {
    const path = `/articles`

    const response = await fetch(`${BASE_URL}${path}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json',
                    'Authorization': `Bearer ${jwt}`
        },
        body: name
    })

    if (response.status === 401) {
        logout()
    } else if (!response.ok) {
        throw new Error(`Failed to fetch table: ${response.status}`)
    }
}