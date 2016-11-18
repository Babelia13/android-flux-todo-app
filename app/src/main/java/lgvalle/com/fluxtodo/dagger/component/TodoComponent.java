package lgvalle.com.fluxtodo.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import lgvalle.com.fluxtodo.TodoActivity;
import lgvalle.com.fluxtodo.actions.ActionsCreator;
import lgvalle.com.fluxtodo.dagger.modules.ActionsCreatorModule;
import lgvalle.com.fluxtodo.dagger.modules.DispatcherModule;
import lgvalle.com.fluxtodo.dagger.modules.TodoStoreModule;
import lgvalle.com.fluxtodo.dispatcher.Dispatcher;
import lgvalle.com.fluxtodo.stores.TodoStore;

@Singleton
@Component(modules = {DispatcherModule.class, ActionsCreatorModule.class, TodoStoreModule.class})
public interface TodoComponent {

    Dispatcher provideDispatcher();

    ActionsCreator provideActionsCreator();

    TodoStore provideTodoStore();

    void inject(TodoActivity activity);

}
