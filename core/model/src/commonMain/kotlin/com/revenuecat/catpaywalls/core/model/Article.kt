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

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Article(
  val title: String,
  val content: String,
  val description: String,
  val author: String,
  val date: String,
  val cover: String,
  // Only premium-only articles carry this key in the feed, so it must default to false when absent.
  val premium: Boolean = false,
) {
  // Generate a stable id from title hashCode since API doesn't provide one
  val id: Long get() = title.hashCode().toLong()
}
