/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2018.flota.flotawebapp.boundary.rest.server;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sv.edu.uesocc.ingenieria.prn335_2018.flota.datos.definicion.Vehiculo;
import sv.edu.uesocc.ingenieria.prn335_2018.flota.flotawebapp.control.VehiculoFacade;

/**
 *
 * @author cristian
 */
@Path("vehiculo")
@Stateless
public class VehiculoResource implements Serializable {

    @Inject
    protected VehiculoFacade vehiculoFacade;

    @PersistenceContext(unitName = "flota")
    protected EntityManager em;

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("id") Long idVehiculo) {
        if (idVehiculo != null) {
            try {
                Vehiculo encontrado = this.vehiculoFacade.find(idVehiculo);
                if (encontrado != null) {
                    return Response.status(Response.Status.OK).entity(encontrado).build();
                }
            } catch (Exception e) {
            }
        }
        return Response.status(Response.Status.NOT_FOUND).header("registro-no-encontrado", idVehiculo).build();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(vehiculoFacade.count());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vehiculo> findAll() {
        try {
            if (vehiculoFacade != null) {
                return vehiculoFacade.findAll();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return Collections.EMPTY_LIST;
    }

    @GET
    @Path("{first}/{tamanio}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vehiculo> findRange(
            @PathParam("first") @DefaultValue("0") int first,
            @PathParam("tamanio") @DefaultValue("10") int size) {
        try {
            if (vehiculoFacade != null) {
                return vehiculoFacade.findRange(first, size);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return Collections.EMPTY_LIST;
    }

    //--------------------------------CRUD---------------------------------------
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void crear(Vehiculo nuevo) {
        try {
            if (vehiculoFacade != null && nuevo != null && nuevo.getIdVehiculo() == null) {
                vehiculoFacade.create(nuevo);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void editar(Vehiculo nuevo) {
        try {
            if (vehiculoFacade != null && nuevo != null) {
                vehiculoFacade.edit(nuevo);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void eliminar(@PathParam("id") int id) {
        try {
            if (vehiculoFacade != null && id > 0) {
                vehiculoFacade.remove(vehiculoFacade.find(id));
            }
        } catch (Exception e) {
            System.out.println("ex: " + e);
        }
    }
    //---------------------------------------------------------------------------
}
