/*
 * Copyright (c) 2025 RevenueCat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.revenuecat.catpaywalls

import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesConfiguration

/**
 * Configures RevenueCat on iOS. Called from Swift (`ConfigurePurchasesKt.configurePurchases`) because the iOS app
 * has no direct RevenueCat dependency: the SDK is bundled inside the ComposeApp framework by purchases-kmp.
 */
fun configurePurchases(apiKey: String) {
  Purchases.logLevel = LogLevel.DEBUG
  Purchases.configure(PurchasesConfiguration(apiKey = apiKey))
}
