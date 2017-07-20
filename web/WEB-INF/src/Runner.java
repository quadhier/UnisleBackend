import dao.*;
import entity.*;
import util.ControllerUtil;
import util.HibernateUtil;
import util.Rewrapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/7/10.
 */
public class Runner {
    private static void printList(List li){
        for(Object o:li)
            System.out.println((String)(((HashMap)o).get("title")) + ((HashMap)o).get("collectdatetime").toString());
    }

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
        String groupID = GroupDAO.createGroup("10000000001","测试组","逗比,学习,J2EE",null,null,"测试描述",null);
        System.out.println(groupID);
        System.out.println(GroupDAO.getDirectorID(groupID));
        GroupDAO.getPositionInGroup("10000000005",groupID);
        System.out.println(GroupDAO.getPositionInGroup("10000000001",groupID));
        System.out.println(GroupDAO.addMember(groupID,"10000000004")?"yes4":"no4");
        System.out.println(GroupDAO.addMember(groupID,"10000000002")?"yes2":"no2");
        System.out.println(GroupDAO.isInGroup("10000000004",groupID)?"in":"not in");
        List list = GroupDAO.getAllMembersID(groupID);
        for(Object o:list)
            System.out.println((String)o);
        System.out.println(GroupDAO.alterPositionInGroup(groupID,"10000000003","manager")?"yesmng3":"nomng3");
        System.out.println(GroupDAO.alterPositionInGroup(groupID,"10000000004","manager")?"yesmng4":"nomng4");
        System.out.println(GroupDAO.alterVisibility("10000000003",groupID,"no")?"yes3":"no3");
        System.out.println(GroupDAO.alterVisibility("10000000002",groupID,"no")?"yes2":"no2");
        System.out.println(GroupDAO.kickoutMember(groupID,"10000000003")?"yesk3":"nok3");
        System.out.println(GroupDAO.kickoutMember(groupID,"10000000002")?"yesk2":"nok2");
        GroupDAO.createGroup("10000000002","testgroup2","J2EE",null,null,null,null);
        GroupDAO.createGroup("10000000004","testgroup3","2E,比赛",null,null,null,null);
        List re1 = GroupDAO.searchGroupByName("test");
        List re2 = GroupDAO.searchGroupByTag("2E");
        List re3 = GroupDAO.searchGroupByTag("比");
        printList(re1);
        printList(re2);
        printList(re3);
        GroupDAO.addMember("30100000002","10000000001");
        GroupDAO.alterVisibility("10000000001","30100000002","no");
        GroupDAO.createGroup("10000000001","testgroup3","test",null,null,null,null);
        GroupDAO.createGroup("10000000001","testgroup4","test",null,null,null,null);
        GroupDAO.updateGroupInfo("30100000002",null,"J2EE,web",null,"software engineer","new description!",null);
        printList(GroupDAO.showGroupsYouAdded("10000000001","all"));
        printList(GroupDAO.showGroupsYouAdded("10000000001","visiable"));
        ActivityDAO.groupPublishActivity("30100000002","group2 published",null);
        GroupDAO.addMember("30100000002","10000000005");
        printList(ControllerUtil.arrToList(ActivityDAO.getActivities("10000000005",new Timestamp(System.currentTimeMillis()),10,"friend")));
        System.out.println(GroupDAO.dismissGroup("30100001000")?"yes":"no");
        System.out.println(GroupDAO.dismissGroup("30100000003")?"yes":"no");
        System.out.println(GroupDAO.changeDirector("30100000001","10000000004")?"yeschange":"nochange");
        System.out.println(?"yes":"no");
        ActivityDAO.cancelPro("20100000039","10000000005");
        ActivityEntity[] array = ActivityDAO.getActivities("10000000001",new Timestamp(System.currentTimeMillis()),10,"friend");
        List list = new ArrayList();
        for(ActivityEntity e:array)
            list.add(e);
        try {
            Rewrapper.wrapList(list, ActivityEntity.class, "11101011");
        }catch(Exception e){
            e.printStackTrace();
        }
        ActivityEntity[] activityEntity = null;
        activityEntity = ActivityDAO.getActivities("10000000001",new Timestamp(System.currentTimeMillis()),10,"friend");
        for(int i=0;i<activityEntity.length;i++)
            if(activityEntity[i] != null)
                System.out.println(activityEntity[i].getActivityid());
            else
                break;
        UuserEntity[] activitycommentEntity = UserInfoDAO.searchNickname("m");
        for(int i=0;i<activitycommentEntity.length;i++)
        if(activitycommentEntity[i] != null)
            System.out.println(activitycommentEntity[i].getNickname());
        else
            break;
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
        );
        System.out.println(ForumDAO.existTheme("摇滚音乐","音乐天地")?"yes":"no");
        System.out.println(ForumDAO.existTheme("流行音乐","音乐天地")?"yes":"no");

        System.out.println(ForumDAO.addTheme("流行音乐","音乐天地")?"yes":"no");
        System.out.println(ForumDAO.addTheme("搞笑音乐","不存在的")?"yes":"no");
        System.out.println(ForumDAO.addTheme("鬼畜音乐","音乐天地")?"yes":"no");

        System.out.println(ForumDAO.deleteTheme("逗比音乐","音乐天地")?"yes":"no");
        System.out.println(ForumDAO.deleteTheme("流行音乐","音乐天地")?"yes":"no");

        System.out.println(ForumDAO.alterThemeName("流行音乐","说唱音乐","音乐天地")?"yes":"no");
        System.out.println(ForumDAO.alterThemeName("鬼畜音乐","哲学音乐","音乐天地")?"yes":"no");
        System.out.println(ForumDAO.calculateExp("10000000009",500)?"yes":"no");
        System.out.println(ForumDAO.calculateExp("10000000004",100)?"yes":"no");
        System.out.println(ForumDAO.calculateExp("10000000005",500)?"yes":"no");
        System.out.println(ForumDAO.publishArticle("10000000003","国家新闻","时事热点","苟利国家生死以","蛤蛤蛤蛤蛤","no"));
        System.out.println(ForumDAO.alterArticleContent("60000000001","江泽民江泽民")?"yes":"no");
        System.out.println(ForumDAO.setVisibilityAndCommentallowed("60000000001","friend","yes")?"yes":"no");
        ForumDAO.publishArticle("10000000001","国家新闻","时事热点","1的第2篇文章","rt","yes");
        ForumDAO.publishArticle("10000000002","国家新闻","时事热点","2的第1篇文章","rt","yes");
        ForumDAO.publishArticle("10000000002","国家新闻","时事热点","2的第2篇文章","rt","yes");
        ForumDAO.publishArticle("10000000003","国家新闻","时事热点","3的第1篇文章","rt","no");
        ForumDAO.publishArticle("10000000003","国家新闻","时事热点","3的第2篇文章","rt","yes");
        ForumDAO.publishArticle("10000000004","国家新闻","时事热点","4的第1篇文章","rt","yes");
        ForumDAO.publishArticle("10000000004","国家新闻","时事热点","4的第2篇文章","rt","yes");
        ForumDAO.setVisibilityAndCommentallowed(
                ForumDAO.publishArticle("10000000005","国家新闻","时事热点","5的第1篇文章","rt","yes"),
                "self",null);
        ForumDAO.publishArticle("10000000005","国家新闻","时事热点","5的第2篇文章","rt","no");
        ForumDAO.publishArticle("10000000005","国家新闻","时事热点","5的第3篇文章","rt","no");
        Timestamp now = new Timestamp(System.currentTimeMillis());
        printList(ForumDAO.getArticleList("10000000004","时事热点","国家新闻",now,0,5));
        printList(ForumDAO.getArticleList("10000000005","时事热点","国家新闻",now,0,20));
        printList(ForumDAO.getMyArticleList("10000000003",2,1));
         System.out.println(ForumDAO.readArticleContent("10000000001","60000000003"));
        ForumDAO.readArticleContent("10000000002","60000000003");
        ForumDAO.readArticleContent("10000000003","60000000003");
        ForumDAO.readArticleContent("10000000003","60000000003");
        ForumDAO.deleteArticle("60000000011");
        System.out.println(ForumDAO.isCommentAllowed("60000000009")?"yes9":"no9");
        System.out.println(ForumDAO.isCommentAllowed("60000000010")?"yes10":"no10");
                ForumDAO.publishArticleComment("10000000003","60000000003","your first comment");
        ForumDAO.publishArticleComment("10000000003","60000000003","your second comment");
        ForumDAO.publishArticleComment("10000000005","60000000003","your 3 comment");

        ForumDAO.saveEditingWords("10000000001","article","editin");
        ForumDAO.saveEditingWords("10000000002","article","editin2");
        System.out.println(ForumDAO.getEditingWords("10000000001","article"));
        System.out.println(ForumDAO.whetherCanDeleteComment("10000000002","10000000005","60000000003")?"yes":"no");
        printList(ForumDAO.getArticleCommentList("60000000003"));
        ForumDAO.saveEditingWords("10000000001","article","editin3");
        ForumDAO.addToCollection("10000000001","60000000003");
        ForumDAO.addToCollection("10000000001","60000000003");
        ForumDAO.addToCollection("10000000002","60000000003");
        ForumDAO.addToCollection("10000000003","60000000003");
        ForumDAO.addToCollection("10000000001","60000000004");
        ForumDAO.deleteFromCollection("10000000003","60000000003");
        ForumDAO.addToCollection("10000000001","60000000001");
        ForumDAO.addToCollection("10000000001","60000000002");
        ForumDAO.addToCollection("10000000001","60000000003");
        ForumDAO.addToCollection("10000000001","60000000004");
        ForumDAO.addToCollection("10000000001","60000000005");
        ForumDAO.addToCollection("10000000001","60000000006");
        ForumDAO.addToCollection("10000000001","60000000007");
        ForumDAO.addToCollection("10000000001","60000000008");
        ForumDAO.addToCollection("10000000001","60000000010");
        printList(ForumDAO.getCollectionList("10000000001",3,4));
        System.out.println(ForumDAO.getPrivilige("10000000001"));
        ForumDAO.alterPrivilige("10000000001",4);
        System.out.println(ForumDAO.getWrappedForumAccountInfo("10000000001").toString());

        System.out.println(?"yes":"no");
        */

        ForumDAO.clearViewHistory("10000000001");



        HibernateUtil.closeSessionFactory();
    }
}
