package com.huaqing.property.base.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleEndedException
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

open class LifecycleViewModel : ViewModel(), IViewModel, LifecycleScopeProvider<LifecycleViewModel.ViewModelEvent> {

    private val lifecycleEvents = BehaviorSubject.createDefault(ViewModelEvent.CREATED)

    override fun lifecycle(): Observable<ViewModelEvent> =
        lifecycleEvents.hide()

    override fun correspondingEvents(): CorrespondingEventsFunction<ViewModelEvent> {
        return CORRESPONDING_EVENTS
    }

    override fun peekLifecycle(): ViewModelEvent? = lifecycleEvents.value

    override fun onCleared() {
        lifecycleEvents.onNext(ViewModelEvent.CLEARED)
        super.onCleared()
    }

    var lifecycleOwner: LifecycleOwner? = null

    @CallSuper
    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    @CallSuper
    override fun onStart(lifecycleOwner: LifecycleOwner) {
    }

    @CallSuper
    override fun onResume(lifecycleOwner: LifecycleOwner) {
    }

    @CallSuper
    override fun onPause(lifecycleOwner: LifecycleOwner) {
    }

    @CallSuper
    override fun onStop(lifecycleOwner: LifecycleOwner) {
    }

    @CallSuper
    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = null
    }

    @CallSuper
    override fun onLifecycleChanged(
        lifecycleOwner: LifecycleOwner,
        event: Lifecycle.Event
    ) {

    }

    enum class ViewModelEvent {
        CREATED, CLEARED
    }

    companion object {
        private val CORRESPONDING_EVENTS = CorrespondingEventsFunction<ViewModelEvent> { event ->
            when (event) {
                ViewModelEvent.CREATED -> ViewModelEvent.CLEARED
                else -> throw LifecycleEndedException(
                    "Cannot bind to ViewModel lifecycle after onCleared."
                )
            }
        }
    }
}