package lgvalle.com.fluxtodo.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lgvalle.com.fluxtodo.actions.ActionsCreator;
import lgvalle.com.fluxtodo.dispatcher.Dispatcher;

@Module
public class ActionsCreatorModule {

    @Singleton
    @Provides
    ActionsCreator provideActionsCreator(Dispatcher dispatcher) {
        return new ActionsCreator(dispatcher);
    }
}
