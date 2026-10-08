package ressources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.List;

@Path("/options")
public class optionResource  {
    OptionBusiness optionBusiness = new OptionBusiness();

    @GET
    @Path("/List")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Option> getAllOptions(){
        return this.optionBusiness.getListeOptions();

    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{code}")
    public Response getByCode(@PathParam("code") int code){
        if (this.optionBusiness.getOptionByCode(code) != null){
            return Response.status(Response.Status.OK).entity(this.optionBusiness.getOptionByCode(code)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    @GET
    @Path("/domaine/{domaine}")
    public List<Option> getOptionsByDomaine(@PathParam("domaine") String domaine) {
        return optionBusiness.getOptionsByDomaine(domaine);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOption(Option option){
        if (this.optionBusiness.addOption(option)){
            return Response.status(Response.Status.CREATED).entity("done").build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{code}")
    public Response updateOption(Option option , @PathParam("code") int code){
        if (optionBusiness.updateOption(code , option)) {
            return Response.status(Response.Status.OK).entity(this.optionBusiness.getOptionByCode(code)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    @DELETE
    @Path("/{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (!optionBusiness.deleteOption(code)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
