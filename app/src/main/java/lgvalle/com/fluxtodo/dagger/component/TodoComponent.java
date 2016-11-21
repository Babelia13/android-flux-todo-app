package lgvalle.com.fluxtodo.dagger.component;

import dagger.Component;
import lgvalle.com.fluxtodo.TodoActivity;
import lgvalle.com.fluxtodo.actions.ActionsCreator;
import lgvalle.com.fluxtodo.dagger.modules.ActionsCreatorModule;
import lgvalle.com.fluxtodo.dagger.modules.TodoStoreModule;
import lgvalle.com.fluxtodo.dagger.scopes.ActivityScope;
import lgvalle.com.fluxtodo.stores.TodoStore;

@ActivityScope
@Component(modules = {ActionsCreatorModule.class, TodoStoreModule.class})
public interface TodoComponent {

    ActionsCreator actionsCreator();

    TodoStore todoStore();

    void inject(TodoActivity activity);

}
