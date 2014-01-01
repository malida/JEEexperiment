/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import servicios.ServicioMensajesLocal;

/**
 *
 * @author Camilo
 */
@WebService(serviceName = "EmergencyWS")
public class EmergencyWS {
    @EJB
    private ServicioMensajesLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "getEmergencias")
    public ArrayList<String> getEmergencias() {
        return ejbRef.getEmergencias();
    }

    @WebMethod(operationName = "addEmergency")
    @Oneway
    public void addEmergency(@WebParam(name = "msg") String msg) {
        ejbRef.addEmergency(msg);
    }
    
}
