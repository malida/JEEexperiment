package servicios;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import pojos.Accidente;
import pojos.Conductor;
import pojos.Tiempo;
import pojos.Trayecto;
import pojos.Trouble;
/**
 *
 * @author MALIDA
 */
public class ServicioPersistencia {
    EntityManagerFactory emf =Persistence.createEntityManagerFactory("Experimento1-ejbPU");
    private EntityManager em;
    UserTransaction utx;
    
    public ServicioPersistencia(){
        this.em = emf.createEntityManager();
    }

    public void create(Object obj){
        if (obj instanceof Trayecto)
        {
            Trayecto m = (Trayecto) obj;
        }
        else if (obj instanceof Accidente)
        {
            Accidente m = (Accidente) obj;
        } 
        else if (obj instanceof Conductor)
        {
            Conductor m = (Conductor) obj;
        } 
        else if (obj instanceof Trouble)
        {
            Trouble m = (Trouble) obj;
            try{
//                utx.begin();
                em.getTransaction().begin();
                em.persist(m);
                em.flush();
                em.getTransaction().commit();
//                utx.commit();
            }catch(Exception e){
//                try {
//                  utx.rollback();
//                } catch (Exception ex) {
//                }
            }
        } 
        else if (obj instanceof Tiempo)
        {
            Tiempo m = (Tiempo) obj;
            try{
//                utx.begin();
                em.getTransaction().begin();
                em.persist(m);
                em.flush();
                em.getTransaction().commit();
//                utx.commit();
            }catch(Exception e){
//                try {
//                    utx.rollback();
//                } catch (Exception ex) {
//                }
            }
        } 
    }

    public void update(Object obj){
    }

    public void delete(Object obj){
    }

    public List findAll(Class c){
        String name = c.getSimpleName();
        return em.createQuery("select c FROM " +name+" c").getResultList();
    }

    public List<Conductor> findAllCon(){
        return (List<Conductor>) em.createQuery("select c FROM Conductor AS c").getResultList();
    }

    public List<Trayecto> findAllTry(){
        return (List<Trayecto>) em.createQuery("select c FROM Trayecto AS c").getResultList();
    }
    public List<Accidente> findAllAcc(){
        return (List<Accidente>) em.createQuery("select c FROM Accidente AS c").getResultList();
    }
    
    public List<Tiempo> findAlltmp(){
        return (List<Tiempo>) em.createQuery("select c FROM Tiempo AS c").getResultList();
    }
    
    public List<Trouble> findAlltrb(){
        return (List<Trouble>) em.createQuery("select c FROM Trouble AS c").getResultList();
    }

    public Accidente findByIdAcc(Long id){
        return em.find(Accidente.class, id);
//        for (Accidente v : findAllAcc())
//        {
//            if (v.getId() == id)
//            {
//                return v;
//            }
//        }
//        return null;
    }
    
    public Trayecto findByIdTry(Long id){
        return em.find(Trayecto.class, id);
    }

    public Conductor findByIdCon(Long id){
        return em.find(Conductor.class, id);
    }
    
    public Object findById(Class c, Long id){
        if (c.equals(Trayecto.class))
        {
            for (Object v : findAll(c))
            {
                Trayecto ven = (Trayecto) v;
                if (ven.getId() == id)
                {
                    return ven;
                }
            }
        } 
        else if (c.equals(Accidente.class))
        {
            for (Object v : findAll(c))
            {
                if (v instanceof Accidente){
                    Accidente mue = (Accidente) v;
                    if (mue.getId() == id)
                    {
                        return mue;
                    }
                }else{
                    System.out.println("shitnigga");
                }                
            }
        } 
        else if (c.equals(Conductor.class))
        {
            for (Object v : findAll(c))
            {
                Conductor con = (Conductor) v;
                if (con.getId() == id)
                {
                    return con;
                }
            }
        }
        return null;
    }    
}
