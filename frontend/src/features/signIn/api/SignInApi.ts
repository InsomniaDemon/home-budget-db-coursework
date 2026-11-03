const BASE_URL = "http://localhost:8080/api";

export async function signInUser(username: string, password: string): Promise<string> {
    const path = `/auth`

    const response = await fetch(`${BASE_URL}${path}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify({username, password})
    })

    if (!response.ok) {
        throw new Error(`Failed to sign in user: ${response.status}`)
    }

    return await response.text();
}