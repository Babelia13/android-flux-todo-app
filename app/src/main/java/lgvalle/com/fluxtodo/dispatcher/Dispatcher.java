package lgvalle.com.fluxtodo.dispatcher;

import android.util.Log;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import lgvalle.com.fluxtodo.actions.Action;

/**
 * Created by lgvalle on 19/07/15.
 */

public class Dispatcher {

    private static final String TAG = "Dispatcher";

    private static final PublishSubject<Action> rxBus = PublishSubject.create();

    public static void dispatch(String type, Object... data) {
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

    public static  <A extends Action> void subscribe(final String actionType, Consumer<Action> subscriber) {
        rxBus.filter(action -> action.getType().equals(actionType)).subscribe(subscriber);
    }

    private static boolean isEmpty(String type) {
        return type == null || type.isEmpty();
    }

}
