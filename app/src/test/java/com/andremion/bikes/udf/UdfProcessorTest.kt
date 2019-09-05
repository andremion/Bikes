package com.andremion.bikes.udf

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
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
        val observer = mock<Observer<ViewEffect>>()

        processor.effects.observeForever(observer)

        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `when trigger effects, should have effects emitted`() {
        val observer = mock<Observer<ViewEffect>>()
        processor.effects.observeForever(observer)

        val firstEmission = ViewEffect()
        processor.emit(firstEmission)
        verify(observer).onChanged(firstEmission)

        val secondEmission = ViewEffect()
        processor.emit(secondEmission)
        verify(observer).onChanged(secondEmission)
    }
}