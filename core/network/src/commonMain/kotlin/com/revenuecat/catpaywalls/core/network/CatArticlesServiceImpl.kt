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
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class CatArticlesServiceImpl(private val httpClient: HttpClient) : CatArticlesService {

  override suspend fun fetchArticles(): Result<List<Article>> = runCatching {
    httpClient.get("$BASE_URL/articles.json").body<List<Article>>()
  }

  companion object {
    private const val BASE_URL =
      "https://gist.githubusercontent.com/skydoves/" +
        "f3836488f8d4f1bf3e3c239759d92c0d/raw/1376ef30281e9c9c0322c88281ea09d572b892a4"
  }
}
