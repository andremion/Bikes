/*
 * Copyright (c) 2019. André Mion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.lifecycle

fun <T, R> LiveData<T>.scan(initialValue: R, accumulator: (R, T) -> R): LiveData<R> = MediatorLiveData<R>().apply {
    value = initialValue
    addSource(this@scan) { emittedValue ->
        value?.let { accumulatedValue ->
            value = accumulator(accumulatedValue, emittedValue)
        }
    }
}
