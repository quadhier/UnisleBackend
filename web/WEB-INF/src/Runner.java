import dao.*;
import entity.*;
import util.HibernateUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
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

        }
        List list = new ArrayList<String>(1);
        list.add("10000000003");
        String id = ActivityDAO.publishActivity("10000000001",list,"my second ancivity!",null);

        UserInfoDAO.createUser("test004@163.com","miku","test","female",null,null,null,null);
        List list = new ArrayList(1);
        list.add("10000000001");
        ActivityDAO.publishActivity("10000000002",list,"2's act",null);
        list = new ArrayList(1);
        list.add("10000000002");
        ActivityDAO.publishActivity("10000000003",list,"3's act",null);
        list = new ArrayList(1);
        list.add("10000000003");
        ActivityDAO.publishActivity("10000000001",list,"1's act",null);

        ActivityEntity[] activityEntity = null;
        activityEntity = ActivityDAO.getActivities("10000000001",new Timestamp(System.currentTimeMillis()),10,"friend");
        for(int i=0;i<activityEntity.length;i++)
            if(activityEntity[i] != null)
                System.out.println(activityEntity[i].getActivityid());
            else
                break;

        activityEntity = null;
        activityEntity = ActivityDAO.getActivities("10000000002",new Timestamp(System.currentTimeMillis()),10,"friend");
        for(int i=0;i<activityEntity.length;i++)
            if(activityEntity[i] != null)
                System.out.println(activityEntity[i].getActivityid());
            else
                break;

        activityEntity = null;
        activityEntity = ActivityDAO.getActivities("10000000003",new Timestamp(System.currentTimeMillis()),10,"I");
        for(int i=0;i<activityEntity.length;i++)
            if(activityEntity[i] != null)
                System.out.println(activityEntity[i].getActivityid());
            else
                break;

        ActivitycommentEntity[] activitycommentEntity = null;
        activitycommentEntity = ActivityDAO.getActivityComments("20100000002");
        for(int i=0;i<activitycommentEntity.length;i++)
            if(activitycommentEntity[i] != null)
                System.out.println(activitycommentEntity[i].getContent());
            else
                break;

          String forwardid = ActivityDAO.forwardActivity("10000000002",null,"20100000004");
           System.out.println(ActivityDAO.getAuthorID("20100000008"));

        */





        /*
        System.out.println(NoticeDAO.deleteSomebodyNotice("1000000001","10000000004")?"yes":"no");
        System.out.println(NoticeDAO.deleteSomebodyNotice("1000000001","1000000004")?"yes":"no");
        System.out.println(NoticeDAO.deleteAKindNotice("1000000004","friendShipAsk")?"yes":"no");
        System.out.println(NoticeDAO.deleteAKindNotice("1000000004","friendshipAsk")?"yes":"no");
        NoticeDAO.sendNotice("1000000002","1000000004","i want to be your friend2","friendshipAsk");
        NoticeDAO.sendNotice("1000000003","1000000004","i want to be your friend3","friendshipAsk");
        NoticeEntity[] noticeIngoreRead = NoticeDAO.getAllNoticeList("1000000004","friendshipAsk");
        NoticeDAO.setNoticeRead(noticeIngoreRead[0].getNoticeEntityPK());
        noticeIngoreRead = NoticeDAO.getNewNoticeList("1000000004","friendshipAsk");
        for(int i=0;i<noticeIngoreRead.length;i++)
            System.out.println(noticeIngoreRead[i].getContent());

        BlacklistEntityPK pk = new BlacklistEntityPK();
        pk.setCoaction("10000000003");
        pk.setCoactee("10000000001");

        BlacklistEntityPK pk2 = new BlacklistEntityPK();
        pk.setCoaction("10000000002");

        BlacklistEntity entity = new BlacklistEntity();
        entity.setBlacklistEntityPK(pk2);
        entity.setCreatedatetime(new Timestamp(System.currentTimeMillis()));

        CommonDAO.updateItem(BlacklistEntity.class,pk,entity);

        FriendshipDAO.addFriendship("10000000001","10000000004");
        ActivityDAO.publishComment("20100000002","10000000002","lzsb+1");
        ActivityDAO.publishComment("20100000002","10000000004","lzsb+2");
        System.out.println(ActivityDAO.whetherCanDeleteComment("10000000004","10000000002","20100000002")?"yes":"no");



        System.out.println(ActivityDAO.cancelPro("20100000001","1000000002")?"yes":"no");

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
