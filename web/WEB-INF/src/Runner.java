import util.HibernateUtil;

import dao.UserInfoDAO;
import entity.ShieldEntity;
import entity.ShieldEntityPK;
import entity.UuserEntity;
import org.hibernate.Session;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/7/10.
 */
public class Runner {
    public static void main(String args[]){
/*
        Session s = null;
        try{
            s = HibernateUtil.getSession();
            s.beginTransaction();

            UuserEntity aUser = new UuserEntity();
            aUser.setUserid("10000000002");
            aUser.setPassword("abc456");
            aUser.setRegisterdatetime(new Timestamp(System.currentTimeMillis()));
            s.save(aUser);
            ShieldEntity shield = new ShieldEntity();
            ShieldEntityPK shieldEntityPK = new ShieldEntityPK();
            shieldEntityPK.setCoactee("10000000001");
            shieldEntityPK.setCoaction("10000000001");
            shieldEntityPK.setActivityid("10200331155");
            shield.setShieldEntityPK(shieldEntityPK);
            s.save(shield);


            s.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            s.getTransaction().rollback();
        }finally{
            if(s!=null)
                s.close();
        }*/

        if(UserInfoDAO.validateUser("test002@163.com","test"))
            System.out.println("yes");
        else
            System.out.println("no");

        System.out.println("User id: " + UserInfoDAO.getUserID("test001@163.com"));
        /*
        UserInfoDAO.createUser("test001@163.com","test001","test","male",null,"测试员",null,null);

        System.out.println(
            UserInfoDAO.seekReuseEmail("9837497@qq.com")?"yes":"no"
        );*/
        HibernateUtil.closeSessionFactory();
    }
}
