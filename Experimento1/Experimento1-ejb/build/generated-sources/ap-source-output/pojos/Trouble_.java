package pojos;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Accidente;
import pojos.Trayecto;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-11-25T10:33:38")
@StaticMetamodel(Trouble.class)
public class Trouble_ { 

    public static volatile SingularAttribute<Trouble, Long> id;
    public static volatile SingularAttribute<Trouble, Trayecto> trayecto_id;
    public static volatile SingularAttribute<Trouble, Accidente> accidente_id;

}