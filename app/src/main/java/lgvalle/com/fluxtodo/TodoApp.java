package lgvalle.com.fluxtodo;

import android.app.Application;

import lgvalle.com.fluxtodo.dagger.component.DaggerTodoComponent;
import lgvalle.com.fluxtodo.dagger.component.TodoComponent;

public class TodoApp extends Application {

    TodoComponent component;


    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerTodoComponent.builder()
                .build();
    }

    public TodoComponent getComponent() {
        return component;
    }
}
