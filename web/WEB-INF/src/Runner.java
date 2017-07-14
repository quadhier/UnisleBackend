import dao.FriendshipDAO;
import util.HibernateUtil;

import dao.UserInfoDAO;

import java.util.List;

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

        /*
        FriendshipDAO.addBlackListItem("10000000002","10000000003");
        FriendshipDAO.addBlackListItem("10000000003","10000000001");
        List l1 = FriendshipDAO.getBlacklist("10000000001");
        for(Object o:l1){
            System.out.println(o.toString());
        }
        System.out.println();
        List l2 = FriendshipDAO.getBlacklist("10000000002");
        for(Object o:l2){
            System.out.println(o.toString());
        }
        System.out.println();
        List l3 = FriendshipDAO.getBlacklist("10000000003");
        for(Object o:l3){
            System.out.println(o.toString());
        }


        FriendshipDAO.setFriendshipNote("10000000002","10000000001","user2's friend");
        FriendshipDAO.deleteBlacklistItem("100111","100222");
        FriendshipDAO.addFriendship("10000000001","10000000002");
        UserInfoDAO.validateToken("100000000038210937097");
        FriendshipDAO.addFriendship("100111","100222");
        FriendshipDAO.addBlackListItem("100111","100222");
        if(FriendshipDAO.deleteFriendship("100222","100111"))
            System.out.println("yes1");
        else
            System.out.println("no1");
        if(FriendshipDAO.existBlacklistItem("100111","100222"))
            System.out.println("yes2");
        else
            System.out.println("no2");

        UserInfoDAO.saveToken("8as76d86ffas86d786","10000000002");
        if(UserInfoDAO.validateToken("8as76d86ffas86d786"))
            System.out.println("yes");
        else
            System.out.println("no");
        UserInfoDAO.deleteToken("a12w143dfs5s86d786");

        if(UserInfoDAO.validateUser("test002@163.com","test"))


        System.out.println("User id: " + UserInfoDAO.getUserID("test001@163.com"));

        UserInfoDAO.createUser("test001@163.com","test001","test","male",null,"测试员",null,null);

        System.out.println(
            UserInfoDAO.seekReuseEmail("9837497@qq.com")?"yes":"no"
        );*/
        HibernateUtil.closeSessionFactory();
    }
}
