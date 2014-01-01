/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author MALIDA
 */
@Entity
public class Tiempo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn
    Trayecto trayecto_id;    
    
    @ManyToOne
    @JoinColumn
    Conductor conductor_id;
    
    @Column
    int tiempo_segundos;

    public Tiempo() {
    }

    public Tiempo(Trayecto trayecto_id, Conductor conductor_id, int tiempo_segundos) {
        this.trayecto_id = trayecto_id;
        this.conductor_id = conductor_id;
        this.tiempo_segundos = tiempo_segundos;
    }

    public Trayecto getTrayecto_id() {
        return trayecto_id;
    }

    public void setTrayecto_id(Trayecto trayecto_id) {
        this.trayecto_id = trayecto_id;
    }

    public Conductor getConductor_id() {
        return conductor_id;
    }

    public void setConductor_id(Conductor conductor_id) {
        this.conductor_id = conductor_id;
    }

    public int getTiempo_segundos() {
        return tiempo_segundos;
    }

    public void setTiempo_segundos(int tiempo_segundos) {
        this.tiempo_segundos = tiempo_segundos;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tiempo)) {
            return false;
        }
        Tiempo other = (Tiempo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojos.Tiempo[ id=" + id + " ]";
    }
    
}
