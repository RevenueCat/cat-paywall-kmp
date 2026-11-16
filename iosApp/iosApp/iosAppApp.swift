import SwiftUI
import ComposeApp

@main
struct iosAppApp: App {

    init() {
        // RevenueCat ships inside the ComposeApp framework, so it is configured through Kotlin.
        ConfigurePurchasesKt.configurePurchases(apiKey: "your_ios_api_key_here")
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
