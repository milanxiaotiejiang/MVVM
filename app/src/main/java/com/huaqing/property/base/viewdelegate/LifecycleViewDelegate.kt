package com.huaqing.property.base.viewdelegate

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

open class LifecycleViewDelegate : IViewDelegate {

    @CallSuper
    override fun onCreate(lifecycleOwner: LifecycleOwner) {

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

    }

    @CallSuper
    override fun onLifecycleChanged(
        lifecycleOwner: LifecycleOwner,
        event: Lifecycle.Event
    ) {

    }
}