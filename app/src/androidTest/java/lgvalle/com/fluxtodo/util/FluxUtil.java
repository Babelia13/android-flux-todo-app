package lgvalle.com.fluxtodo.util;

import android.content.Context;

import lgvalle.com.fluxtodo.TodoApp;
import lgvalle.com.fluxtodo.stores.TodoStore;

public class FluxUtil {

    @SuppressWarnings("unchecked")
    public static TodoStore findStore(Context context) {
        return ((TodoApp) context.getApplicationContext()).getComponent().todoStore();
    }
}
