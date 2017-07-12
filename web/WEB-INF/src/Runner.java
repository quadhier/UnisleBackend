import Utils.HibernateUtil;
import models.TestUser;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.File;

/**
 * Created by Administrator on 2017/7/10.
 */
public class Runner {
    public static void main(String args[]){
        Session s = null;
        try{
            s = HibernateUtil.getSession();
            s.beginTransaction();

            TestUser aUser = new TestUser();
            aUser.setUserID("123456");
            aUser.setPassword("34567");
            s.save(aUser);

            s.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            s.getTransaction().rollback();
        }finally{
            if(s!=null)
                s.close();
        }
        System.out.print("success");

    }
}
