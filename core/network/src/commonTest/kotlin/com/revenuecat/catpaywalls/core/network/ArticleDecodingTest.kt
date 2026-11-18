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
package com.revenuecat.catpaywalls.core.network

import com.revenuecat.catpaywalls.core.model.Article
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ArticleDecodingTest {

  @Test
  fun articleWithoutPremiumKey_decodesAsNotPremium() {
    val articles = catArticlesJson.decodeFromString<List<Article>>("[${articleJson(premiumField = "")}]")

    assertEquals(1, articles.size)
    assertFalse(articles.single().premium)
  }

  @Test
  fun articleWithPremiumTrue_decodesAsPremium() {
    val articles =
      catArticlesJson.decodeFromString<List<Article>>("[${articleJson(premiumField = ", \"premium\": true")}]")

    assertEquals(1, articles.size)
    assertTrue(articles.single().premium)
  }

  // Same shape as an entry of the articles.json gist.
  private fun articleJson(premiumField: String): String =
    """
    {
      "title": "Modern Android Testing",
      "content": "Content",
      "description": "Description",
      "author": "Author",
      "date": "2025-01-01",
      "cover": "https://example.com/cover.jpg"$premiumField
    }
    """.trimIndent()
}
