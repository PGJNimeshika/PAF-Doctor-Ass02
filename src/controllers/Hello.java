package controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
  
@Path("/hello")
public class Hello {
  
    @GET
    @Path("/{name}")
    @Produces("application/json")
    public Response getMsg(@PathParam("name") String name) {
  
        
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("your_name", name);
 
		String result = jsonObject.toString();
		return Response.status(200).entity(result).build();
  
    }
  
}
