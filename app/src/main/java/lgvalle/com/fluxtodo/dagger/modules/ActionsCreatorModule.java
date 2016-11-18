package lgvalle.com.fluxtodo.dagger.modules;

import dagger.Module;
import dagger.Provides;
import lgvalle.com.fluxtodo.actions.ActionsCreator;
import lgvalle.com.fluxtodo.dagger.scopes.ActivityScope;

@Module
public class ActionsCreatorModule {

    @ActivityScope
    @Provides
    ActionsCreator provideActionsCreator(){
        return new ActionsCreator();
    }
}
