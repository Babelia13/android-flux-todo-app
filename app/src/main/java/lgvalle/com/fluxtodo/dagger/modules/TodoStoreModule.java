package lgvalle.com.fluxtodo.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lgvalle.com.fluxtodo.dispatcher.Dispatcher;
import lgvalle.com.fluxtodo.stores.TodoStore;

@Module
public class TodoStoreModule {

    @Singleton
    @Provides
    public TodoStore provideTodoStore(Dispatcher dispatcher) {
        return new TodoStore(dispatcher);
    }
}
