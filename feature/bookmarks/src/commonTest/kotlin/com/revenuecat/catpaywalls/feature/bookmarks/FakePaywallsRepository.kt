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
package com.revenuecat.catpaywalls.feature.bookmarks

import com.revenuecat.catpaywalls.core.data.PaywallsRepository
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.Offering
import com.revenuecat.purchases.kmp.models.StoreTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePaywallsRepository : PaywallsRepository {
  private val notSet = IllegalStateException("not set")

  override fun fetchOffering(): Flow<Result<Offering>> = flow { emit(Result.failure(notSet)) }

  override fun fetchCustomerInfo(): Flow<Result<CustomerInfo>> = flow { emit(Result.failure(notSet)) }

  override fun awaitPurchase(packageId: String): Flow<Result<StoreTransaction>> = flow { emit(Result.failure(notSet)) }
}
