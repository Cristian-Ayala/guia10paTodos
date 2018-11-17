/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2018.flota.parcial.vehiculoclient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import sv.edu.uesocc.ingenieria.prn335_2018.flota.datos.definicion.Vehiculo;

/**
 *
 * @author cristian
 */
@Named
@ViewScoped
public class client implements Serializable {

    Client cliente;
    private WebTarget target;
    private List<Vehiculo> salida;
    private int desde=0;
    private int cuantos=10;
    private Vehiculo vehiculo = new Vehiculo();

    //Debido a que instanciar un cliente a cada momento es muy costoso, sólo lo haremos una vez y lo reutilizaremos
    public client() {
        try {
            cliente = ClientBuilder.newClient();
            target = cliente.target("http://localhost:8080/VEHICULOrest/rest/vehiculo");
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void actualizar(ActionEvent al) {
        init();
    }

    public List filtro(int desde, int cuantos) {
        try {
            target = cliente.target("http://localhost:8080/VEHICULOrest/rest/vehiculo/{first}/{tamanio}").resolveTemplate("first", desde).resolveTemplate("tamanio", cuantos);
            salida = target.request(MediaType.APPLICATION_JSON).get(new GenericType<List<Vehiculo>>(){});
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        } 
        return salida;
    }

    @PostConstruct
    public void init() {
        filtro(desde, cuantos);
        System.out.println("\nDesde: " + desde+"\nCuantos: "+cuantos);
    }

    //una vez nuestro recogedor de basura recoja las instancias que no son utilizadas o referenciadas, nuestro cliente se cerrrará
    @Override
    protected void finalize() throws IllegalStateException {
        try {
            if (this.cliente != null) {
                this.cliente.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    //--------------------------------------OPERACIONES-CRUD-------------------------------------------

     public Vehiculo crear(Vehiculo rol1) {
        if (cliente != null) {
            Vehiculo salida1 = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(rol1, MediaType.APPLICATION_JSON), Vehiculo.class);
            if (salida1 != null && salida1.getIdVehiculo() == null) {
                return salida1;
            }
        }
        return null;
    }
     
     public Vehiculo editar(Vehiculo rol1) {
        if (rol1 != null) {
            try {
                Vehiculo salida1 = target.request(MediaType.APPLICATION_JSON).put(Entity.entity(rol1, MediaType.APPLICATION_JSON), Vehiculo.class);
            if (salida1 != null ) {
                return salida1;
            }
            } catch (Exception e) {
                System.out.println("ERROR: "+e);
            }
        }
        return null;
    }
     
     public Vehiculo eliminar(Vehiculo rol1) {
        System.out.println("ENTRO A ELIMINAR PRRO");
        
        if (rol1 != null) {
              System.out.println(rol1.getIdVehiculo());
            try {
                Vehiculo salida1 = target.path(rol1.getIdVehiculo().toString()).request(MediaType.APPLICATION_JSON).delete( Vehiculo.class);
            System.out.println("ENTRO A ELIMINAR");
            if (salida1 != null ) {
                return salida1;
            }
            } catch (Exception e) {
                System.out.println("ERROR: "+e);
            }
       }
         return null;
   }
    //--------------------------------------setters-y-getters------------------------------------------
    public List<Vehiculo> getSalida() {
        return salida;
    }

    public void setSalida(List<Vehiculo> salida) {
        this.salida = salida;
    }

    public int getDesde() {
        return desde;
    }

    public void setDesde(int desde) {
        this.desde = desde;
    }

    public int getCuantos() {
        return cuantos;
    }

    public void setCuantos(int cuantos) {
        this.cuantos = cuantos;
    }
    
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
    
    

}
