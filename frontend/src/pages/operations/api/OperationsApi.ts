const BASE_URL = "http://localhost:8080/api";

export async function getObjectTable(jwt: string, logout: () => void): Promise<object[]> {
    const path = `/op`

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
        throw new Error(`Failed to sign in user: ${response.status}`)
    }

    return await response.json();
}