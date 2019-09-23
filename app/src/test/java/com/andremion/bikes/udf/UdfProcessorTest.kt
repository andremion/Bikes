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