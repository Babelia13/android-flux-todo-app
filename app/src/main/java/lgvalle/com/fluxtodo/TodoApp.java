package lgvalle.com.fluxtodo;

import android.app.Application;

import lgvalle.com.fluxtodo.dagger.component.DaggerDispatcherComponent;
import lgvalle.com.fluxtodo.dagger.component.DaggerTodoComponent;
import lgvalle.com.fluxtodo.dagger.component.DispatcherComponent;
import lgvalle.com.fluxtodo.dagger.component.TodoComponent;

public class TodoApp extends Application {

    TodoComponent component;

    DispatcherComponent dispatcherComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        //Parent component
        dispatcherComponent = DaggerDispatcherComponent.builder().build();

        //Dependent component. Parent component declaration is needed (.dispatcherComponent())
        component = DaggerTodoComponent.builder()
                .dispatcherComponent(dispatcherComponent)
                .build();
    }

    public TodoComponent getComponent() {
        return component;
    }
}
