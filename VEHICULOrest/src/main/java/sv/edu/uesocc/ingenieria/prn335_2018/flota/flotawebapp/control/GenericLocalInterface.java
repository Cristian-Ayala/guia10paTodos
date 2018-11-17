/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2018.flota.flotawebapp.control;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cristian
 * @param <T>
 */
@Local
public interface GenericLocalInterface<T> {

    void create(T clase);

    void edit(T clase);

    void remove(T clase);

    T find(Object id);

    List<T> findAll();

    List<T> findRange(int desde, int cuantos);

    int count();
    
}
