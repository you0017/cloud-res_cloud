package com.sh.openfeign;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
 
@Produces("application/json")
public interface GitHub2_javax {
    @GET
    @Path("/repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(@PathParam("owner") String owner, @PathParam("repo") String repo);
 
}