package it.siletto.ms.auth;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.DropwizardAppRule;
import it.siletto.ms.auth.dto.TokenResponseDTO;
import it.siletto.ms.auth.dto.WhoAmIResponseDTO;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class TestWhoami {

	@ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE = new DropwizardAppRule<AppConfiguration>(AuthServiceApp.class, "app.yml");
	
	@Test
	public void testLoginAndWhoami() throws Exception {

		Client client = Client.create();

		System.out.println("login...");
		
		WebResource login = client.resource(
				String.format("http://localhost:%d/token/generate?username=pippo&password=pluto", RULE.getLocalPort())
				);
		ClientResponse response = login.accept("application/json").get(ClientResponse.class);

		assertThat(response.getStatus()).isEqualTo(200);

		TokenResponseDTO token = response.getEntity(TokenResponseDTO.class);

		System.out.println("status:" + token.getStatus());
		System.out.println("expires:" + token.getExpires());
		System.out.println("token:" + token.getToken());

		assertThat(token.getStatus()).isEqualTo("OK");
		
		WebResource whoami = client.resource(String.format("http://localhost:%d/whoami", RULE.getLocalPort()));
		ClientResponse response2 = whoami.header("Authentication", "Bearer "+token.getToken()).accept("application/json").get(ClientResponse.class);
		
		assertThat(response2.getStatus()).isEqualTo(200);

		System.out.println("whoami...");
		
		WhoAmIResponseDTO wai = response2.getEntity(WhoAmIResponseDTO.class);
		System.out.println("user: "+wai.getUsername());
		System.out.println("roles: "+wai.getRoles());

		assertThat(wai.getUsername()).isEqualTo("pippo");

	}
}
