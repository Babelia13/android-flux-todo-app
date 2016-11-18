package lgvalle.com.fluxtodo.dagger.component;

import dagger.Component;
import lgvalle.com.fluxtodo.TodoActivity;
import lgvalle.com.fluxtodo.actions.ActionsCreator;
import lgvalle.com.fluxtodo.dagger.modules.ActionsCreatorModule;
import lgvalle.com.fluxtodo.dagger.modules.TodoStoreModule;
import lgvalle.com.fluxtodo.dagger.scopes.ActivityScope;
import lgvalle.com.fluxtodo.stores.TodoStore;

@ActivityScope //Using the previously defined scope in child component, note that @Singleton will not work
@Component(dependencies = DispatcherComponent.class, modules = {ActionsCreatorModule.class, TodoStoreModule.class})
public interface TodoComponent {

    ActionsCreator provideActionsCreator();

    TodoStore provideTodoStore();

    void inject(TodoActivity activity);

}
