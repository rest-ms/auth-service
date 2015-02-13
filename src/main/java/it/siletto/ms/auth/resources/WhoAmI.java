package it.siletto.ms.auth.resources;

import io.dropwizard.jersey.caching.CacheControl;
import it.siletto.ms.auth.RestrictedTo;
import it.siletto.ms.auth.User;
import it.siletto.ms.auth.dto.WhoAmIResponseDTO;
import it.siletto.ms.base.cors.Cors;
import it.siletto.ms.base.cors.CorsPreflight;
import it.siletto.ms.base.resources.BaseResource;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

@Path("/whoami")
@Produces(MediaType.APPLICATION_JSON)
public class WhoAmI extends BaseResource {

	@GET
	@Timed
	@CacheControl(noCache = true)
	@Cors
	public WhoAmIResponseDTO whoami(@RestrictedTo("user") User user) {
		
		WhoAmIResponseDTO ret = new WhoAmIResponseDTO();
		ret.setUsername(user.getUserName());
		ret.setRoles(user.getAuthorities());
		return ret;

	}
	
	@OPTIONS
	@CorsPreflight(headers="Authentication")
	public void preflight(){}



}
