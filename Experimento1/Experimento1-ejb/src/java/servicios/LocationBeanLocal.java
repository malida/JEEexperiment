/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import javax.ejb.Local;

/**
 *
 * @author Camilo
 */
@Local
public interface LocationBeanLocal {
    
    public Double getCurrentLat();
    

    public void setCurrentLat(Double currentLat);
    

    public Double getCurrentLong();
    

    public void setCurrentLong(Double currentLong);
    
}
