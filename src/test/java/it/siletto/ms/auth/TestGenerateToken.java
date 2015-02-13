package it.siletto.ms.auth;

import static org.fest.assertions.api.Assertions.assertThat;
import io.dropwizard.testing.junit.DropwizardAppRule;
import it.siletto.ms.auth.dto.EncryptedTokenDTO;
import it.siletto.ms.auth.dto.TokenResponseDTO;
import it.siletto.ms.auth.service.impl.CypherServiceRSAImpl;

import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class TestGenerateToken {
	
	@ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE = new DropwizardAppRule<AppConfiguration>(AuthServiceApp.class, "app.yml");

	@Test
	public void testLogin() throws Exception {
		
		Client client = Client.create();

		System.out.println("login...");
		
		WebResource login = client.resource(
					String.format("http://localhost:%d/token/generate?username=pippo&password=pluto", RULE.getLocalPort())
				);
		ClientResponse response = login.accept("application/json").get(ClientResponse.class);

		assertThat(response.getStatus()).isEqualTo(200);
		
		TokenResponseDTO res = response.getEntity(TokenResponseDTO.class);

		System.out.println("token:" + res.getToken());
				
		String tokenData = new CypherServiceRSAImpl().decrypt(RULE.getConfiguration().getPrivateKeyFile(), res.getToken());
		System.out.println("tokenData:" + tokenData);
		
		ObjectMapper mapper = new ObjectMapper();
		EncryptedTokenDTO dto = mapper.readValue(tokenData, EncryptedTokenDTO.class);
		
		assertThat("pippo").isEqualTo(dto.getUsername());
	}
}
