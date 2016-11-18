package lgvalle.com.fluxtodo.dagger.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application application;

    public AppModule(Application application){
        this.application = application;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return application;
    }

}
