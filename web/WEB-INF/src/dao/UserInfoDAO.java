package dao;
import Utils.HibernateUtil;
import org.hibernate.Session;

/**
 * Created by Administrator on 2017/7/12.
 */
public class UserInfoDAO {
    /**
     * 此方法用于尝试添加新用户
     * 返回值：0：添加成功；1：
     */
    public static boolean seekReusePhone(int phoneNumber){
        return true;
    }
    public static boolean seekReuseEmail(String email){
        return true;
    }
    /*
    public static boolean addUser(int phoneNumber,String email,String password,String name,StringBuffer generatedID){
        Session s = null;
        User newUser = new User();
        try{
            s = HibernateUtil.getSession();
            s.beginTransaction();

            newUser.setPassword(password);
            newUser.setName(name);
            newUser.setTelephone(phoneNumber);
            newUser.setEmail(email);
            s.save(newUser);
        }catch (Exception e){
            e.printStackTrace();
            s.getTransaction().rollback();
            return false;
        }finally{
            HibernateUtil.safeCloseSession(s);
        }
        generatedID = new StringBuffer(newUser.getUserID());

        return true;
    }*/
}
