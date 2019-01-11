package com.huaqing.property.ext.viewmodel

import androidx.lifecycle.LifecycleOwner
import com.huaqing.property.base.viewmodel.LifecycleViewModel

fun LifecycleViewModel.addLifecycle(activity: androidx.fragment.app.FragmentActivity) =
        activity inject this

fun LifecycleViewModel.addLifecycle(fragment: androidx.fragment.app.Fragment) =
        fragment inject this

fun LifecycleViewModel.addLifecycle(lifecycleOwner: LifecycleOwner) =
        lifecycleOwner inject this

private infix fun <A : LifecycleOwner, B : LifecycleViewModel> A.inject(viewModel: B) =
        lifecycle.addObserver(viewModel)
