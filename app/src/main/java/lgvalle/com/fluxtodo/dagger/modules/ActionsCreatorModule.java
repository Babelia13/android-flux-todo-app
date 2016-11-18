package lgvalle.com.fluxtodo.dagger.modules;

import dagger.Module;
import dagger.Provides;
import lgvalle.com.fluxtodo.actions.ActionsCreator;
import lgvalle.com.fluxtodo.dagger.scopes.ActivityScope;
import lgvalle.com.fluxtodo.dispatcher.Dispatcher;

@Module
public class ActionsCreatorModule {

    @ActivityScope //Needs to be consistent with the component scope
    @Provides
    ActionsCreator provideActionsCreator(Dispatcher dispatcher) {
        return new ActionsCreator(dispatcher);
    }
}
