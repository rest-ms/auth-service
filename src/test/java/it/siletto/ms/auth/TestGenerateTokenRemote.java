package it.siletto.ms.auth;

import static org.fest.assertions.api.Assertions.assertThat;
import it.siletto.ms.auth.dto.TokenResponseDTO;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class TestGenerateTokenRemote {

	public static void main(String[] args) {
		
		Client client = Client.create();

		System.out.println("login...");
		
		WebResource login = client.resource(
				"http://192.168.1.112:8080/token/generate?username=pippo&password=pluto"
				);
		ClientResponse response = login.accept("application/json").get(ClientResponse.class);

		assertThat(response.getStatus()).isEqualTo(200);
		
		TokenResponseDTO res = response.getEntity(TokenResponseDTO.class);

		System.out.println("token:" + res.getToken());
		
	}
}
