package it.siletto.ms.auth;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import it.siletto.ms.auth.resources.GenerateToken;
import it.siletto.ms.auth.resources.WhoAmI;
import it.siletto.ms.auth.service.CypherService;
import it.siletto.ms.auth.service.impl.CypherServiceRSAImpl;
import it.siletto.ms.base.cors.CorsFilterFactory;
import it.siletto.ms.base.health.BasicHealthCheck;
import it.siletto.ms.identity.service.IdentityDAO;
import it.siletto.ms.identity.service.impl.IdentityDAOMockImpl;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class AuthServiceApp extends Application<AppConfiguration> {
    private static AppConfiguration cfg;

    public static AppConfiguration getConfig() {
        return cfg;
    }

    public static void main(String[] args) throws Exception {
        new AuthServiceApp().run(args);
    }

    public AuthServiceApp() {

    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> configBootstrap) {
    }

    @SuppressWarnings("unchecked")
	@Override
    public void run(AppConfiguration appConfiguration, Environment environment) throws Exception {
        cfg = appConfiguration;

        Injector injector = Guice.createInjector(new Module(){
        	@Override
        	public void configure(Binder binder) {
        		binder.bind(IdentityDAO.class).to(IdentityDAOMockImpl.class);
        		binder.bind(CypherService.class).to(CypherServiceRSAImpl.class);
        	}
        });
        
        CredentialsAuthenticator authenticator = injector.getInstance(CredentialsAuthenticator.class);
        authenticator.setPrivateKeyFile(appConfiguration.getPrivateKeyFile());

        environment.jersey().register(injector.getInstance(GenerateToken.class));
        environment.jersey().register(injector.getInstance(WhoAmI.class));

        environment.healthChecks().register("test", new BasicHealthCheck());

        environment.jersey().register(new RestrictedToProvider<User>(authenticator, "MyRealm"));
		
        environment.jersey().getResourceConfig().getResourceFilterFactories().add(new CorsFilterFactory());
                
    }
}
