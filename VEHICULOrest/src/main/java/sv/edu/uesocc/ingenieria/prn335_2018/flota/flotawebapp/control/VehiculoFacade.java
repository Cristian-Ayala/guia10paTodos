/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2018.flota.flotawebapp.control;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sv.edu.uesocc.ingenieria.prn335_2018.flota.datos.definicion.Vehiculo;

/**
 *
 * @author cristian
 */
@Stateless
@LocalBean
public class VehiculoFacade extends AbstractFacade<Vehiculo> implements GenericLocalInterface<Vehiculo> {

    @PersistenceContext(unitName = "flota")
    private EntityManager ema;

    @Override
    protected EntityManager getEntityManager() {
        return this.ema;
    }

    public VehiculoFacade() {
        super(Vehiculo.class);
    }
    
}
