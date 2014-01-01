package pojos;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Conductor;
import pojos.Trayecto;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-11-25T10:33:38")
@StaticMetamodel(Tiempo.class)
public class Tiempo_ { 

    public static volatile SingularAttribute<Tiempo, Long> id;
    public static volatile SingularAttribute<Tiempo, Trayecto> trayecto_id;
    public static volatile SingularAttribute<Tiempo, Conductor> conductor_id;
    public static volatile SingularAttribute<Tiempo, Integer> tiempo_segundos;

}