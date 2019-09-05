package com.andremion.bikes.udf

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Rule
import org.junit.Test

class UdfDispatcherImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private class Action
    private class ViewState
    private class Result
    private class ViewEffect

    private val processor: UdfProcessor<Action, Result, ViewEffect> = mock()
    private val reducer: UdfReducer<ViewState, Result> = mock()
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
        val observer = mock<Observer<ViewState>>()

        dispatcher.states.observeForever(observer)

        verify(observer).onChanged(initialViewState)
    }

    @Test
    fun `when start observing with previous emission, should emit that state`() {
        val aViewState = ViewState()
        whenever(processor.invoke(any())).thenReturn(MutableLiveData<Result>().apply {
            value = Result()
        })
        whenever(reducer.invoke(any(), any())).thenReturn(aViewState)
        val observer = mock<Observer<ViewState>>()

        dispatcher.submit(Action())
        dispatcher.states.observeForever(observer)

        verify(observer).onChanged(aViewState)
    }

    @Test
    fun `should not emmit the same state in a row`() {
        val anAction = Action()
        val aViewState = ViewState()
        whenever(processor.invoke(anAction)).thenReturn(MutableLiveData<Result>().apply {
            value = Result()
        })
        whenever(reducer.invoke(any(), any())).thenReturn(aViewState)
        val observer = mock<Observer<ViewState>>()

        dispatcher.states.observeForever(observer)
        dispatcher.submit(anAction, anAction)

        verify(observer).onChanged(aViewState)
    }
}
