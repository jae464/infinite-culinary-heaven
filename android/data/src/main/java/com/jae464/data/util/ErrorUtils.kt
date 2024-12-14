package com.jae464.data.util

fun makeErrorResponse(code: Int, body: String?): String {
    return """
        {
            code: $code,           
            body: $body
        }
    """.trimIndent()
}