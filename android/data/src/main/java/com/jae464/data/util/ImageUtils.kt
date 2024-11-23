package com.jae464.data.util

fun adjustLocalhostUrl(url: String): String {
    return url.replace("localhost", "10.0.2.2")
}