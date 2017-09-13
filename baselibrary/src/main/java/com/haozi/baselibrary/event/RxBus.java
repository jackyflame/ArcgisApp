package com.haozi.baselibrary.event;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus {

    private static volatile RxBus instance = new RxBus();

    private Subject<Object> bus = PublishSubject.create().toSerialized();

    public static RxBus getInstance() {
        RxBus rxBus = instance;
        if (rxBus == null) {
            synchronized (RxBus.class) {
                rxBus = instance;
                if (rxBus == null) {
                    rxBus = instance = new RxBus();
                }
            }
        }
        return rxBus;
    }

    public void post(Object event) {
        bus.onNext(event);
    }

    public <T extends BaseEvent> Observable<T> asObservable(final Class<T> aClass) {
//        return bus.filter(new Predicate<Object>() {
//                    @Override
//                    public boolean test(@NonNull Object event) throws Exception {
//                        return aClass.isInstance(event);
//                    }
//                })
//                .cast(aClass);
        return bus.ofType(aClass);
    }

    public Observable<BaseEvent> asObservable() {
        return bus.cast(BaseEvent.class);
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }

}
