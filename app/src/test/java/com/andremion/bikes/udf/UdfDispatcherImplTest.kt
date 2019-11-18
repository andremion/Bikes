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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class UdfDispatcherImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private class Action
    private class ViewState
    private class Result
    private class ViewEffect

    private val processor: UdfProcessor<Action, Result, ViewEffect> = mockk(relaxed = true)
    private val reducer: UdfReducer<ViewState, Result> = mockk()
    private val initialViewState = ViewState()

    private val dispatcher by lazy {
        UdfDispatcherImpl(
            processor,
            reducer,
            initialViewState
        )
    }

    @Test
    fun `when start observing with no previous emission, should emit the initial state`() {
        val observer = mockk<Observer<ViewState>>(relaxed = true)

        dispatcher.states.observeForever(observer)

        verify { observer.onChanged(initialViewState) }
    }

    @Test
    fun `when start observing with previous emission, should emit that state`() {
        val aViewState = ViewState()
        every { processor.invoke(any()) } returns MutableLiveData<Result>().apply {
            value = Result()
        }
        every { reducer.invoke(any(), any()) } returns aViewState
        val observer = mockk<Observer<ViewState>>(relaxed = true)

        dispatcher.submit(Action())
        dispatcher.states.observeForever(observer)

        verify { observer.onChanged(aViewState) }
    }

    @Test
    fun `should not emmit the same state in a row`() {
        val anAction = Action()
        val aViewState = ViewState()
        every { processor.invoke(anAction) } returns MutableLiveData<Result>().apply {
            value = Result()
        }
        every { reducer.invoke(any(), any()) } returns aViewState
        val observer = mockk<Observer<ViewState>>(relaxed = true)

        dispatcher.states.observeForever(observer)
        dispatcher.submit(anAction, anAction)

        verify { observer.onChanged(aViewState) }
    }
}
