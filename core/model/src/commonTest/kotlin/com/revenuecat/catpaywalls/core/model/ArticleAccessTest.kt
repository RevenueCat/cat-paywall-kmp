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
package com.revenuecat.catpaywalls.core.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ArticleAccessTest {
  private val freeArticle = MockUtils.mockArticle
  private val premiumArticle = MockUtils.mockArticle.copy(title = "Premium Cat Title", premium = true)

  @Test
  fun entitledUser_opensFreeArticleOverQuota() {
    // Without the entitlement step this input would hit the daily-limit paywall.
    val access = resolveArticleAccess(freeArticle, isEntitled = true, todayReadCount = 5, dailyFreeLimit = LIMIT)

    assertEquals(ArticleAccess.Open, access)
  }

  @Test
  fun nonEntitledUser_premiumArticleAtQuota_getsPremiumPaywall() {
    val access =
      resolveArticleAccess(premiumArticle, isEntitled = false, todayReadCount = LIMIT, dailyFreeLimit = LIMIT)

    assertEquals(ArticleAccess.PremiumPaywall, access)
  }

  @Test
  fun nonEntitledUser_freeArticleAtOrOverQuota_getsDailyLimitPaywall() {
    assertEquals(
      ArticleAccess.DailyLimitPaywall,
      resolveArticleAccess(freeArticle, isEntitled = false, todayReadCount = LIMIT, dailyFreeLimit = LIMIT),
    )
    assertEquals(
      ArticleAccess.DailyLimitPaywall,
      resolveArticleAccess(freeArticle, isEntitled = false, todayReadCount = LIMIT + 1, dailyFreeLimit = LIMIT),
    )
  }

  @Test
  fun nonEntitledUser_freeArticleUnderQuota_opensArticle() {
    val access =
      resolveArticleAccess(freeArticle, isEntitled = false, todayReadCount = LIMIT - 1, dailyFreeLimit = LIMIT)

    assertEquals(ArticleAccess.Open, access)
  }

  @Test
  fun entitledUser_premiumArticleAtOrOverQuota_opensArticle() {
    assertEquals(
      ArticleAccess.Open,
      resolveArticleAccess(premiumArticle, isEntitled = true, todayReadCount = LIMIT, dailyFreeLimit = LIMIT),
    )
    assertEquals(
      ArticleAccess.Open,
      resolveArticleAccess(premiumArticle, isEntitled = true, todayReadCount = LIMIT + 2, dailyFreeLimit = LIMIT),
    )
  }

  @Test
  fun nonEntitledUser_premiumArticleWithZeroReads_getsPremiumPaywallNotQuotaPaywall() {
    val access = resolveArticleAccess(premiumArticle, isEntitled = false, todayReadCount = 0, dailyFreeLimit = LIMIT)

    assertEquals(ArticleAccess.PremiumPaywall, access)
  }

  @Test
  fun premiumPart_locksPremiumArticleOnlyWithoutEntitlement() {
    assertTrue(requiresPremiumPaywall(premiumArticle, isEntitled = false))
    assertFalse(requiresPremiumPaywall(premiumArticle, isEntitled = true))
    assertFalse(requiresPremiumPaywall(freeArticle, isEntitled = false))
    assertFalse(requiresPremiumPaywall(freeArticle, isEntitled = true))
  }

  private companion object {
    const val LIMIT = 3
  }
}
