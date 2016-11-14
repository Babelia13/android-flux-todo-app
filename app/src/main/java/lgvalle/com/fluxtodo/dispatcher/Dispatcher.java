package lgvalle.com.fluxtodo.dispatcher;

import android.util.Log;

import java.util.HashMap;

import lgvalle.com.fluxtodo.actions.Action;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Created by lgvalle on 19/07/15.
 */
public class Dispatcher {

    private static final String TAG = "Dispatcher";
    private static Dispatcher instance;

    private final PublishSubject<Action> rxBus = PublishSubject.create();

    public static Dispatcher get() {
        if (instance == null) {
            instance = new Dispatcher();
        }
        return instance;
    }

    public Dispatcher() {

    }

    public void dispatch(String type, Object... data) {
        Log.d(TAG, "[dispatch] Type: " + type + ", Data: " + data.toString());
        if (isEmpty(type)) {
            throw new IllegalArgumentException("Type must not be empty");
        }

        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("Data must be a valid list of key,value pairs");
        }

        Action.Builder actionBuilder = Action.type(type);
        int i = 0;
        while (i < data.length) {
            String key = (String) data[i++];
            Object value = data[i++];
            actionBuilder.bundle(key, value);
        }

        rxBus.onNext(actionBuilder.build());
    }

    public <A extends Action> void subscribe(final String actionType, Subscriber<Action> subscriber) {
        rxBus.filter(new Func1<Action, Boolean>() {

            @Override
            public Boolean call(Action action) {
                return action.getType().equals(actionType);
            }
        }).subscribe(subscriber);
    }

    private boolean isEmpty(String type) {
        return type == null || type.isEmpty();
    }

}
