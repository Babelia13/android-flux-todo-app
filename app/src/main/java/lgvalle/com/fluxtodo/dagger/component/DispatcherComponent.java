package lgvalle.com.fluxtodo.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import lgvalle.com.fluxtodo.dagger.modules.DispatcherModule;
import lgvalle.com.fluxtodo.dispatcher.Dispatcher;
//TODO component not needed, Dispatcher is static
@Singleton
@Component(modules = DispatcherModule.class)
public interface DispatcherComponent {

    Dispatcher provideDispatcher();
}
