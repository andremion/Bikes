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

package com.andremion.bikes.udf

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class UdfProcessorTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private class Action
    private class Result
    private class ViewEffect

    private val processor = object : UdfProcessor<Action, Result, ViewEffect>() {
        override fun invoke(action: Action): LiveData<Result> = MutableLiveData()

        fun emit(vararg effects: ViewEffect) {
            trigger(*effects)
        }
    }

    @Test
    fun `when start observing, should emit no effect`() {
        val observer = mockk<Observer<ViewEffect>>()

        processor.effects.observeForever(observer)

        verify { observer wasNot Called }
    }

    @Test
    fun `when trigger effects, should have effects emitted`() {
        val observer = mockk<Observer<ViewEffect>>(relaxed = true)
        processor.effects.observeForever(observer)

        val firstEmission = ViewEffect()
        processor.emit(firstEmission)
        verify { observer.onChanged(firstEmission) }

        val secondEmission = ViewEffect()
        processor.emit(secondEmission)
        verify { observer.onChanged(secondEmission) }
    }
}
