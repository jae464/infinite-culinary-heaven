package com.jae464.data.util

fun makeErrorResponse(code: Int, message: String, body: String): String {
    return """
        {
            code: $code,
            message: $message,
            body: $body
        }
    """.trimIndent()
}