package lgvalle.com.fluxtodo.stores;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import io.reactivex.Flowable;
import rx.Subscriber;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by lgvalle on 02/08/15.
 */
public abstract class Store<S> {

    private static final String TAG = "Store";

    @Nullable
    private S state;
    private final PublishSubject<S> publisher = PublishSubject.create();

    protected abstract S initialState();

    public void subscribe(Subscriber subscriber) {
        Log.d(TAG, "[subscribe] Subscribe: " + subscriber);
        publisher.subscribe(subscriber);
    }

    @NonNull
    public final S state() {
        if (state == null) {
            state = initialState();
        }
        return state;
    }

    public final void setState(@NonNull S newState) {
        Log.d(TAG, "[setState] Old state: " + state + ", New state: " + newState);
        if (newState.equals(state())) {
            Log.d(TAG, "[setState] States are equals");
            return;
        }
        state = newState;
        publisher.onNext(state);
    }


}
