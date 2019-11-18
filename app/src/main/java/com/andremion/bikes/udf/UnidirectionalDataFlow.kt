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

import androidx.lifecycle.*

abstract class UdfViewModel<in Action, Result, ViewState, ViewEffect>(
    private val dispatcher: UdfDispatcher<Action, ViewState, ViewEffect>
) : ViewModel(), UdfDispatcher<Action, ViewState, ViewEffect> by dispatcher

abstract class UdfProcessor<in Action, Result, ViewEffect> : (Action) -> LiveData<Result> {

    private val _effects = MutableLiveData<ViewEffect>()
    val effects: LiveData<ViewEffect> = _effects

    protected fun trigger(vararg effects: ViewEffect) {
        effects.forEach { _effects.value = it }
    }
}

interface UdfReducer<ViewState, Result> : (ViewState, Result) -> ViewState

class UdfDispatcherImpl<in Action, out Result, ViewState, ViewEffect>(
    processor: UdfProcessor<Action, Result, ViewEffect>,
    reducer: UdfReducer<ViewState, Result>,
    initialViewState: ViewState
) : UdfDispatcher<Action, ViewState, ViewEffect> {

    private val actions: MutableLiveData<Action> = MutableLiveData()
    override val states: LiveData<ViewState>
    override val effects: LiveData<ViewEffect> = processor.effects

    init {
        states = actions
            .switchMap(processor)
            .scan(initialViewState, reducer)
            .distinctUntilChanged()
    }

    override fun submit(vararg actions: Action) {
        actions.forEach { this.actions.value = it }
    }
}

interface UdfDispatcher<in Action, ViewState, ViewEffect> {
    val states: LiveData<ViewState>
    val effects: LiveData<ViewEffect>
    fun submit(vararg actions: Action)
}
