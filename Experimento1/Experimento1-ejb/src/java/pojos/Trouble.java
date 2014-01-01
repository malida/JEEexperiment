/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import java.io.Serializable;
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
public class Trouble implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn
    Trayecto trayecto_id;
    
    @ManyToOne
    @JoinColumn        
    Accidente accidente_id;

    public Trouble() {
    }

    public Trouble(Trayecto trayecto_id, Accidente accidente_id) {
        this.trayecto_id = trayecto_id;
        this.accidente_id = accidente_id;
    }

    public Trayecto getTrayecto_id() {
        return trayecto_id;
    }

    public void setTrayecto_id(Trayecto trayecto_id) {
        this.trayecto_id = trayecto_id;
    }

    public Accidente getAccidente_id() {
        return accidente_id;
    }

    public void setAccidente_id(Accidente accidente_id) {
        this.accidente_id = accidente_id;
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
        if (!(object instanceof Trouble)) {
            return false;
        }
        Trouble other = (Trouble) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojos.Trouble[ id=" + id + " ]";
    }

    
}
