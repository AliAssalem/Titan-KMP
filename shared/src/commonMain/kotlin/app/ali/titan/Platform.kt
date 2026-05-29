package app.ali.titan

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform