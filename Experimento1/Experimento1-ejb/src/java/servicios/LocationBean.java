/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import javax.ejb.Singleton;

/**
 *
 * @author Camilo
 */
@Singleton
public class LocationBean implements LocationBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private Double currentLat, currentLong;
    
    @Override
    public Double getCurrentLat() {
        return currentLat;
    }
    @Override
    public void setCurrentLat(Double currentLat) {
        this.currentLat = currentLat;
    }

    @Override
    public Double getCurrentLong() {
        return currentLong;
    }
    

    @Override
    public void setCurrentLong(Double currentLong) {
        this.currentLong = currentLong;
    }
    
    
    
    
    
    
    

}
