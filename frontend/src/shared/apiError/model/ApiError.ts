export class ApiError extends Error {
    status: number
    code: string

    constructor(status: number, code: string, message: string) {
        super(message)
        this.status = status
        this.code = code
    }
}

export async function toApiError(response: Response): Promise<ApiError> {
    try {
        const data = await response.json()
        return new ApiError(response.status, data.error ?? "UNKNOWN", data.message ?? "Unknown error")
    } catch {
        return new ApiError(response.status, "UNKNOWN", `Request failed: ${response.status}`)
    }
}