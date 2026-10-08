package ressources;

import entities.Etudiant;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/etudiants")
public class EtudiantRessource {

    EtudiantBusiness etudiantBusiness = new EtudiantBusiness();
    OptionBusiness optionBusiness = new OptionBusiness();

    // les etud lkoll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Etudiant> getAllEtudiants() {
        return etudiantBusiness.getAllEtudiants();
    }

    // njibou etudiant b l identifiant mteew
    @GET
    @Path("/{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantByIdentifiant(
            @PathParam("identifiant") String identifiant) {

        Etudiant etudiant =
                etudiantBusiness.getEtudiantByIdentifiant(identifiant);

        if (etudiant != null) {
            return Response.status(Response.Status.OK)
                    .entity(etudiant)
                    .build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addEtudiant(Etudiant etudiant) {

        if (etudiantBusiness.addEtudiant(etudiant)) {
            return Response.status(Response.Status.CREATED)
                    .entity("done")
                    .build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/{identifiant}")
    public Response deleteEtudiant(
            @PathParam("identifiant") String identifiant) {

        if (etudiantBusiness.deleteEtudiant(identifiant)) {
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // nbadlou fi etudiant
    @PUT
    @Path("/{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(
            Etudiant etudiant,
            @PathParam("identifiant") String identifiant) {

        if (etudiantBusiness.updateEtudiant(identifiant, etudiant)) {
            return Response.status(Response.Status.OK)
                    .entity(etudiantBusiness.getEtudiantByIdentifiant(identifiant))
                    .build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // jib les étudiants b loption
    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_XML)
    public List<Etudiant> getEtudiantsByOption(
            @QueryParam("codeOption") int codeOption) {

        Option option = optionBusiness.getOptionByCode(codeOption);

        if (option == null) {
            throw new NotFoundException();
        }

        return etudiantBusiness.getEtudiantsByOption(option);
    }
}
