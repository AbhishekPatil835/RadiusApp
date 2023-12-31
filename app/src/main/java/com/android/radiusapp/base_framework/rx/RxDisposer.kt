package com.android.radiusapp.base_framework.rx

import com.android.radiusapp.base_framework.rx.Disposer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class RxDisposer : Disposer<Disposable> {
    @Volatile private var disposable: CompositeDisposable? = null

    override fun Disposable.collect() {
        if (disposable == null || disposable?.isDisposed == true) {
            disposable = CompositeDisposable()
        }

        disposable?.add(this)
    }

    override fun dispose() {
        disposable?.clear()
        disposable = null
    }
}
