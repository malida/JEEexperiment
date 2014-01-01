/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author Camilo
 */
@Local
public interface ServicioMensajesLocal {
    
    
    public ArrayList<String> getEmergencias();
    
    public void addEmergency(String msg);

    
}
