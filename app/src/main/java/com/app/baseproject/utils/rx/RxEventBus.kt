package com.app.baseproject.utils.rx

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A simple event bus built with RxJava
 */
@Singleton
class RxEventBus @Inject
constructor() {

    private val mBackpressureStrategy = BackpressureStrategy.BUFFER
    private val mBusSubject: PublishSubject<Any> = PublishSubject.create()

    /**
     * Posts an object (usually an Event) to the bus
     */
    fun post(event: Any) {
        mBusSubject.onNext(event)
    }

    /**
     * Observable that will emmit everything posted to the event bus.
     */
    fun observable(): Flowable<Any> {
        return mBusSubject.toFlowable(mBackpressureStrategy)
    }

    /**
     * Observable that only emits events of a specific class.
     * Use this if you only want to subscribe to one type of events.
     */
    fun <T> filteredObservable(eventClass: Class<T>): Flowable<T> {
        return mBusSubject.ofType(eventClass).toFlowable(mBackpressureStrategy)
    }
}

