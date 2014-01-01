/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import utils.Location;

/**
 *
 * @author Camilo
 */
@WebService(serviceName = "MobibusLocationWS")
public class MobibusLocationWS {
    
    @EJB
    private servicios.LocationBeanLocal locationEJB;

    

    @WebMethod(operationName = "getMobibusPosition")
    public String getMobibusPosition() {
        Location l = new Location();
        l.setLatitud(locationEJB.getCurrentLat());
        l.setLongitud(locationEJB.getCurrentLong());
        
        String r = ""+l.getLatitud() +" : "+l.getLongitud();
        
        return r;
    }
    
    
}
