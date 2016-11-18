package lgvalle.com.fluxtodo.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import lgvalle.com.fluxtodo.dagger.modules.DispatcherModule;
import lgvalle.com.fluxtodo.dispatcher.Dispatcher;

@Singleton
@Component(modules = DispatcherModule.class)
public interface DispatcherComponent {

    Dispatcher provideDispatcher();
}
