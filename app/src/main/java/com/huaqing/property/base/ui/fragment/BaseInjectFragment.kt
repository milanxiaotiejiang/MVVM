package com.huaqing.property.base.ui.fragment

import androidx.fragment.app.Fragment
import com.huaqing.property.base.ui.IView
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.kcontext

abstract class BaseInjectFragment : AutoDisposeFragment(), KodeinAware, IView {
    protected val parentKodein by closestKodein()
    override val kodeinContext = kcontext<Fragment>(this)
}