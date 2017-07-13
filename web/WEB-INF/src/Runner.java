import Utils.HibernateUtil;

import entity.ShieldEntity;
import entity.ShieldEntityPK;
import org.hibernate.Session;

/**
 * Created by Administrator on 2017/7/10.
 */
public class Runner {
    public static void main(String args[]){
        System.out.print("0qqqqqqqqqq");
        Session s = null;
        try{
            System.out.print("0000000000000");
            s = HibernateUtil.getSession();
            s.beginTransaction();
            System.out.print("11111111111");
            /*UuserEntity aUser = new UuserEntity();
            aUser.setUserid("10000000001");
            aUser.setTelephone("123456");
            aUser.setPassword("34567");
            aUser.setName("nn");
            System.out.print("22222222222");
            s.save(aUser);*/
            ShieldEntity shield = new ShieldEntity();
            ShieldEntityPK shieldEntityPK = new ShieldEntityPK();
            shieldEntityPK.setCoactee("10000000001");
            shieldEntityPK.setCoaction("10000000001");
            shieldEntityPK.setActivityid("10200331155");
            shield.setShieldEntityPK(shieldEntityPK);
            s.save(shield);
            System.out.print(3);
            s.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            s.getTransaction().rollback();
        }finally{
            if(s!=null)
                s.close();
        }
        System.out.print("success");
        HibernateUtil.closeSessionFactory();
    }
}
