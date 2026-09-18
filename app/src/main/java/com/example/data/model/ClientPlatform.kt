package com.example.data.model

enum class ClientPlatform(
    val displayName: String,
    val systemTag: String,
    val deviceModel: String,
    val ipAddress: String,
    val protocol: String
) {
    ANDROID("Android App", "CLIENT_ANDROID_NATIVE", "Pixel 9 Pro Fold (Host)", "127.0.0.1:8080", "Native IPC / Binder"),
    IPHONE("iPhone App", "CLIENT_IOS_SWIFTUI", "iPhone 16 Pro Max", "192.168.1.142:9001", "gRPC / TLS v1.3"),
    WEB("Web App", "CLIENT_WEB_WASM", "Chrome 134 / Next.js", "192.168.1.105:3000", "WSS / Binary Stream"),
    WINDOWS("Windows App", "CLIENT_WIN_ELECTRON", "ThinkPad P1 Gen 7 (Win 11)", "192.168.1.220:4430", "Named Pipe / RPC");

    companion object {
        fun fromTag(tag: String): ClientPlatform {
            return entries.find { it.name.equals(tag, ignoreCase = true) || it.systemTag.equals(tag, ignoreCase = true) } ?: ANDROID
        }
    }
}
