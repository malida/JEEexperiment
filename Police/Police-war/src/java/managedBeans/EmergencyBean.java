/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;
import servicios.ServicioMensajesLocal;

/**
 *
 * @author Camilo
 */
@ManagedBean
@SessionScoped
public class EmergencyBean {

    @EJB
    private ServicioMensajesLocal servicio;
    
    /**
     * Creates a new instance of EmergencyBean
     */
    public EmergencyBean() {
    }
    
    public ArrayList<String> getEmergencias()
    {
        return servicio.getEmergencias();
    }
    
    
    
}
