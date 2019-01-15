package com.huaqing.property.ui.splash

import android.animation.ValueAnimator
import android.view.View
import com.bumptech.glide.request.transition.ViewPropertyTransition
import com.huaqing.property.R
import com.huaqing.property.base.glide.utils.loadImage
import com.huaqing.property.base.ui.BaseActivity
import com.huaqing.property.common.helper.RxSchedulers
import com.huaqing.property.databinding.ActivitySplashBinding
import com.huaqing.property.ext.autodispose.bindLifecycle
import com.huaqing.property.ui.login.LoginActivity
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_splash.*
import org.kodein.di.Kodein
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val valueDuration: Long = 800
    private val wowDuration: Long = 1200

    override val kodein = Kodein.lazy {
        extend(parentKodein)
        import(splashKodeinModule)
    }

    override val layoutId = R.layout.activity_splash

    var valueAnimator = ValueAnimator()

    var mDisposable: Disposable? = null

    override fun initView() {

        valueAnimator = ValueAnimator.ofFloat(0F, 1F)

        valueAnimator.setDuration(valueDuration)

        valueAnimator.addUpdateListener {
            val value = it.getAnimatedValue() as Float

            ivSplash.scaleX = ((0.5 + 0.5 * value).toFloat())
            ivSplash.scaleY = ((0.5 + 0.5 * value).toFloat())
            ivSplash.alpha = value
        }

        wowSplash.startAnimate(wowDuration)
        wowSplash.setOnEndListener {

            wowSplash.visibility = View.GONE
            wowView.visibility = View.VISIBLE
            wowView.startAnimate(wowSplash.getDrawingCache())

            loadImage(this@SplashActivity, ivSplash, R.mipmap.splash_full, ViewPropertyTransition.Animator {
                valueAnimator.start()
            })
        }

        val duration = (valueDuration + wowDuration + 3000) / 1000
        mDisposable = Flowable.intervalRange(0, duration, 0, 1, TimeUnit.SECONDS)
            .subscribeOn(RxSchedulers.io)
            .observeOn(RxSchedulers.ui)
            .doOnNext {
                skip.text =
                        "${this@SplashActivity.resources.getString(R.string.click_skip)} ${(duration - 1 - it)} s "

            }
            .doOnComplete {
                toLogin()
            }
            .bindLifecycle(this)
            .subscribe()
    }

    fun toLogin(v: View) {
        toLogin()
    }

    fun toLogin() = LoginActivity.launch(this)

    override fun onDestroy() {
        if (valueAnimator.isRunning) {
            valueAnimator.cancel()
        }
        if (!mDisposable!!.isDisposed) {
            mDisposable!!.dispose()
        }
        super.onDestroy()
    }

}
