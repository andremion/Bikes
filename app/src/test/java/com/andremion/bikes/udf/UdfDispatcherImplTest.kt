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
