package lgvalle.com.fluxtodo.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lgvalle.com.fluxtodo.dispatcher.Dispatcher;

@Module
public class DispatcherModule {

    @Singleton
    @Provides
    public Dispatcher provideDispatcher() {
        return new Dispatcher();
    }
}
