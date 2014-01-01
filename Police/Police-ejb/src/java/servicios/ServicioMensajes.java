/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.ArrayList;
import javax.ejb.Singleton;


/**
 *
 * @author Camilo
 */
@Singleton
public class ServicioMensajes implements ServicioMensajesLocal {
    
    private ArrayList<String> emergencias;

    public ServicioMensajes() {
        if (emergencias==null) 
        {
            emergencias = new ArrayList<>(); System.out.println("NUEVO ARRAY!");
        }
        
    }
    
    

    @Override
    public ArrayList<String> getEmergencias() {
        return emergencias;
    }

    @Override
    public void addEmergency(String msg) {
        System.out.println("POLICIA: mensaje emergencia: "+msg);
        emergencias.add(msg);
        System.out.println("POLICIA: se agrego emergencia a la cola");
    }
    
}
