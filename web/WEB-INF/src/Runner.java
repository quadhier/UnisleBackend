import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Administrator on 2017/7/10.
 */
public class Runner {
    public static void main(String args[]){
        SessionFactory f = new Configuration().configure().buildSessionFactory();
        Session s = f.openSession();
        s.beginTransaction();
        try{
            User aUser = new User();
            aUser.setName("12345");
            aUser.setPassword("34567");
            s.save(aUser);
        }catch (Exception e){
            s.getTransaction().rollback();
        }

        s.getTransaction().commit();
        System.out.print("success");
        s.close();
        f.close();
    }
}
