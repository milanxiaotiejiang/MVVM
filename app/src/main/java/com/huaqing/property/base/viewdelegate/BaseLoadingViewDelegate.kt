package com.huaqing.property.base.viewdelegate

import com.huaqing.property.common.loadings.CommonLoadingViewModel
import com.huaqing.property.common.loadings.ILoadingDelegate

abstract class BaseLoadingViewDelegate(
    val loadingViewModel: CommonLoadingViewModel
) : BaseViewDelegate(), ILoadingDelegate by loadingViewModel