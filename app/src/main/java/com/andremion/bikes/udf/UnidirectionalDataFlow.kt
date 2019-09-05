package com.andremion.bikes.udf

import androidx.lifecycle.*

/**
 * A [ViewModel] subclass that that is delegated to [UdfDispatcherImpl] to handle all the Unidirectional Data Flow.
 */
abstract class UdfViewModel<in Action, Result, ViewState, ViewEffect>(
    private val dispatcher: UdfDispatcher<Action, ViewState, ViewEffect>
) : ViewModel(), UdfDispatcher<Action, ViewState, ViewEffect> by dispatcher

/**
 * Processor: [Action] => [Result] / [ViewEffect]
 *
 * It receives an [Action], process some operation (fetching/saving from/to network or database) and returns an [LiveData] of [Result] that will be used by [UdfReducer].
 * Additionally it may trigger a [ViewEffect] that means that it should be emitted but not kept as state such as:
 * Showing a Toast, Navigating to another screen, etc.
 */
abstract class UdfProcessor<in Action, Result, ViewEffect> : (Action) -> LiveData<Result> {

    private val _effects = MutableLiveData<ViewEffect>()
    val effects: LiveData<ViewEffect> = _effects

    protected fun trigger(vararg effects: ViewEffect) {
        effects.forEach { _effects.value = it }
    }
}

/**
 * Reducer: Current [ViewState] + [Result] => New [ViewState]
 *
 * It receives a [Result] from [UdfProcessor] and takes the current [ViewState] to produce another [ViewState].
 */
interface UdfReducer<ViewState, Result> : (ViewState, Result) -> ViewState

/**
 * Handles all the Unidirectional Data Flow.
 */
class UdfDispatcherImpl<in Action, out Result, ViewState, ViewEffect>(
    processor: UdfProcessor<Action, Result, ViewEffect>,
    reducer: UdfReducer<ViewState, Result>,
    initialViewState: ViewState
) : UdfDispatcher<Action, ViewState, ViewEffect> {

    private val actions: MutableLiveData<Action> = MutableLiveData()
    override val states: LiveData<ViewState>
    override val effects: LiveData<ViewEffect> = processor.effects

    // https://github.com/oldergod/android-architecture/blob/todo-mvi-rxjava-kotlin/app/src/main/java/com/example/android/architecture/blueprints/todoapp/tasks/TasksViewModel.kt
    init {
        states = actions
            .switchMap(processor)
            // Cache each state and pass it to the reducer to create a new state from
            // the previous cached one and the latest result emitted from the processor.
            // The Scan operator is used here for the caching.
            .scan(initialViewState, reducer)
            // When a reducer just emits previous state, there's no reason to emmit it again.
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
