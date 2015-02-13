package it.siletto.ms.auth.resources;

import io.dropwizard.jersey.caching.CacheControl;
import it.siletto.ms.base.cors.Cors;
import it.siletto.ms.base.resources.BaseResource;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

@Path("/testCors")
@Produces(MediaType.APPLICATION_JSON)
public class TestCors extends BaseResource {

	@GET
	@Timed
	@CacheControl(noCache = true)
	@Cors
	public Map<String,String> testCors() {
				
		Map<String,String> ret = new HashMap<String,String>();
		ret.put("hello", "world");
		return ret;

	}



}
