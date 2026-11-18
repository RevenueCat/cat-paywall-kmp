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

/** Where tapping an article should take the user. */
enum class ArticleAccess {
  Open,
  PremiumPaywall,
  DailyLimitPaywall,
}

/**
 * The access rule for opening an article from the home grid, evaluated in order:
 * the premium entitlement opens everything, a premium article requires that entitlement whatever today's
 * read count is, and only then does the free daily quota apply.
 */
fun resolveArticleAccess(
  article: Article,
  isEntitled: Boolean,
  todayReadCount: Int,
  dailyFreeLimit: Int,
): ArticleAccess = when {
  isEntitled -> ArticleAccess.Open
  requiresPremiumPaywall(article, isEntitled) -> ArticleAccess.PremiumPaywall
  todayReadCount >= dailyFreeLimit -> ArticleAccess.DailyLimitPaywall
  else -> ArticleAccess.Open
}

/**
 * Only the premium part of [resolveArticleAccess]. Bookmarks use this alone because tapping a bookmark was
 * never gated by the daily quota, but a lapsed subscriber must still not open a bookmarked premium article.
 */
fun requiresPremiumPaywall(article: Article, isEntitled: Boolean): Boolean = article.premium && !isEntitled
