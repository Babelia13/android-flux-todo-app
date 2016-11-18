package lgvalle.com.fluxtodo.dagger.modules;

import dagger.Module;
import dagger.Provides;
import lgvalle.com.fluxtodo.dagger.scopes.ActivityScope;
import lgvalle.com.fluxtodo.dispatcher.Dispatcher;
import lgvalle.com.fluxtodo.stores.TodoStore;

@Module
public class TodoStoreModule {

    @ActivityScope //Needs to be consistent with the component scope
    @Provides
    public TodoStore provideTodoStore(Dispatcher dispatcher) {
        return new TodoStore(dispatcher);
    }
}
