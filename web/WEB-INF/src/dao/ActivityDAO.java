/*
    注意：(...) not in null 在sql中会报错。null不等于空集。
 */
package dao;

import entity.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityDAO {
    private ActivityDAO(){}
    //tested
    public static String publishActivity(String userid,List shieldIDList,String content,String attachment){
        Session s = null;
        Session c = null;
        String newActivityID = null;
        try{
            c = HibernateUtil.getSession();
            Criteria counter = c.createCriteria(ActivityEntity.class);
            counter.setProjection(Projections.max("activityid"));
            long result = Long.parseLong((String)counter.uniqueResult());
            newActivityID = String.valueOf(result+1);

            s = HibernateUtil.getSession();
            ActivityEntity activity = new ActivityEntity();
            activity.setActivityid(newActivityID);
            activity.setPublisher(userid);
            activity.setContent(content);
            activity.setPros((short)0);
            activity.setPublicdatetime(new Timestamp(System.currentTimeMillis()));
            activity.setType("original");
            if(attachment != null)activity.setAttachment(attachment);
            s.beginTransaction();
            s.save(activity);
            s.getTransaction().commit();
        }catch(Exception e){
            s.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }finally {
            HibernateUtil.safeCloseSession(s);
            HibernateUtil.safeCloseSession(c);
        }

        if(shieldIDList!=null  && !shieldIDList.isEmpty()) {
            try {
                s = HibernateUtil.getSession();
                List shieldEntityList = new ArrayList<ShieldEntity>(shieldIDList.size());
                for(Object o:shieldIDList){
                    ShieldEntityPK pk = new ShieldEntityPK();
                    pk.setActivityid(newActivityID);
                    pk.setCoaction(userid);
                    pk.setCoactee(o.toString());
                    ShieldEntity entity = new ShieldEntity();
                    entity.setShieldEntityPK(pk);
                    shieldEntityList.add(entity);
                }
                s.beginTransaction();
                for(int i=0;i<shieldEntityList.size();i++){
                    s.save(shieldEntityList.get(i));
                    if(i%24==0){
                        s.flush();
                        s.clear();
                    }
                }
                s.getTransaction().commit();
            }catch (Exception e){
                e.printStackTrace();
                s.getTransaction().rollback();
                deleteActivity(newActivityID);
                return null;
            }finally {
                HibernateUtil.safeCloseSession(s);
            }
        }

        return newActivityID;
    }

    public static String groupPublishActivity(String groupid,String content,String attachment){
        Session s = null;
        Session c = null;
        String newActivityID = null;
        try{
            c = HibernateUtil.getSession();
            Criteria counter = c.createCriteria(ActivityEntity.class);
            counter.setProjection(Projections.max("activityid"));
            long result = Long.parseLong((String)counter.uniqueResult());
            newActivityID = String.valueOf(result+1);

            s = HibernateUtil.getSession();
            ActivityEntity activity = new ActivityEntity();
            activity.setActivityid(newActivityID);
            activity.setPublisher(groupid);
            activity.setContent(content);
            activity.setPros((short)0);
            activity.setPublicdatetime(new Timestamp(System.currentTimeMillis()));
            activity.setType("group");
            if(attachment != null)activity.setAttachment(attachment);
            s.beginTransaction();
            s.save(activity);
            s.getTransaction().commit();
        }catch(Exception e){
            s.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }finally {
            HibernateUtil.safeCloseSession(s);
            HibernateUtil.safeCloseSession(c);
        }
        return newActivityID;
    }
    //tested
    public static String forwardActivity(String userid,List shieldIDList,String originalActivity){
        ActivityEntity original = (ActivityEntity) CommonDAO.getItemByPK(ActivityEntity.class,originalActivity);
        Session s = null;
        Session c = null;
        String newActivityID = null;
        try{
            c = HibernateUtil.getSession();
            Criteria counter = c.createCriteria(ActivityEntity.class);
            counter.setProjection(Projections.rowCount());
            long result = (Long)counter.uniqueResult();
            String newActivityIDBuilder = String.format("%08d",result+1);
            newActivityID = "201" + newActivityIDBuilder;

            s = HibernateUtil.getSession();
            ActivityEntity activity = new ActivityEntity();
            activity.setActivityid(newActivityID);
            activity.setPublisher(userid);
            activity.setContent(original.getContent());
            activity.setPros((short)0);
            activity.setPublicdatetime(new Timestamp(System.currentTimeMillis()));
            activity.setType("forwarded");
            activity.setOriginalactivityid(original.getActivityid());
            if(original.getAttachment() != null)activity.setAttachment(original.getAttachment());
            s.beginTransaction();
            s.save(activity);
            s.getTransaction().commit();
        }catch(Exception e){
            s.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }finally {
            HibernateUtil.safeCloseSession(s);
            HibernateUtil.safeCloseSession(c);
        }

        if(shieldIDList!=null  && !shieldIDList.isEmpty()) {
            try {
                s = HibernateUtil.getSession();
                List shieldEntityList = new ArrayList<ShieldEntity>(shieldIDList.size());
                for(Object o:shieldIDList){
                    ShieldEntityPK pk = new ShieldEntityPK();
                    pk.setActivityid(newActivityID);
                    pk.setCoaction(userid);
                    pk.setCoactee(o.toString());
                    ShieldEntity entity = new ShieldEntity();
                    entity.setShieldEntityPK(pk);
                    shieldEntityList.add(entity);
                }
                s.beginTransaction();
                for(int i=0;i<shieldEntityList.size();i++){
                    s.save(shieldEntityList.get(i));
                    if(i%24==0){
                        s.flush();
                        s.clear();
                    }
                }
                s.getTransaction().commit();
            }catch (Exception e){
                e.printStackTrace();
                s.getTransaction().rollback();
                deleteActivity(newActivityID);
                return null;
            }finally {
                HibernateUtil.safeCloseSession(s);
            }
        }

        return newActivityID;
    }
    //tested
    public static String getAuthorID(String activityID){
        ActivityEntity entity = (ActivityEntity) CommonDAO.getItemByPK(ActivityEntity.class,activityID);
        while(entity.getType().equals("forwarded")){
            String originalid = entity.getOriginalactivityid();
            entity = (ActivityEntity) CommonDAO.getItemByPK(ActivityEntity.class,originalid);
            /*
                这里如果把originalid变成后边的表达式代入entity的赋值语句
                ，理论上是可以的，但实际上会产生BUG。可以从此了解到编译方式
                并不是从后向前。神奇的bug。
             */
        }

        return entity.getPublisher();
    }
    //tested
    //!!!如果没有预先配置好activityComment、pros、shield的外键依赖和级联删除，该方法不能正确运行
    public static boolean deleteActivity(String activityID){
        Session s = null;
        try{
            s = HibernateUtil.getSession();
            ActivityEntity fordelete = s.get(ActivityEntity.class,activityID);
            if(fordelete==null)
                return true;

            s.beginTransaction();
            s.delete(fordelete);
            s.getTransaction().commit();
        }catch(Exception e){
            s.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return true;
    }

    public static ActivityEntity[] getActivities(String userid,Timestamp lastdate,int number,String view){
        Session s = null;
        List resultList = null;
        try{
            s = HibernateUtil.getSession();
            if(view.equals("self")) {
                String hql = "from ActivityEntity as activity where activity.publicdatetime<=:current and activity.publisher=:userid order by activity.publicdatetime desc";
                Query query = s.createQuery(hql);
                query.setParameter("current",lastdate);
                query.setParameter("userid",userid);
                query.setFirstResult(0);
                query.setMaxResults(number);
                resultList = query.list();
            }else if(view.equals("friend")){
                String getIdHql = "select entity.groupmemberEntityPK.groupid from GroupmemberEntity entity where entity.groupmemberEntityPK.userid =:uid";
                Map<String,Object> params = new HashMap<>();
                params.put("uid",userid);
                List groupidList = CommonDAO.queryHql(getIdHql,params);
                List friendList = FriendshipDAO.getFriendIDList(userid);
                friendList.add(userid);

                String hql = "from ActivityEntity activity " +
                        "where activity.publicdatetime<=:current and " +
                        "((activity.publisher in :plist " +
                        "and :publisherVisibilityI not in (select user.activityvisibility from UuserEntity user where user.id =activity.publisher)" +
                        "and :userid not in (select shield.shieldEntityPK.coactee from ShieldEntity shield " +
                        "where shield.shieldEntityPK.coaction=activity.publisher and shield.shieldEntityPK.activityid = activity.activityid))" +
                        "or (activity.type=:gtype and activity.publisher=:glist))" +
                        "order by activity.publicdatetime desc";
                Query query = s.createQuery(hql);
                query.setParameter("current",lastdate);
                query.setParameter("plist",friendList);
                query.setParameter("publisherVisibilityI","self");
                query.setParameter("userid",userid);
                query.setParameter("gtype","group");
                query.setParameter("glist",groupidList);
                query.setFirstResult(0);
                query.setMaxResults(number);
                resultList = query.list();
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        if(resultList.isEmpty())
            return null;
        ActivityEntity[] entityArray = new ActivityEntity[resultList.size()];
        for(int i=0;i<resultList.size();i++){
            entityArray[i] = (ActivityEntity) resultList.get(i);
        }
        return entityArray;
    }
    //tested
    public static boolean makePro(String activityID,String userID){
        Session s = null;
        try{
            s = HibernateUtil.getSession();
            String hql = "from ProEntity pro where pro.proEntityPK.activityid =:actvtid and pro.proEntityPK.userid =:uid";
            Query query = s.createQuery(hql);
            query.setParameter("actvtid",activityID);
            query.setParameter("uid",userID);
            List list = query.list();
            if(!list.isEmpty())
                return true;

            ProEntityPK pk = new ProEntityPK();
            pk.setActivityid(activityID);
            pk.setUserid(userID);
            ProEntity pro = new ProEntity();
            pro.setProEntityPK(pk);
            pro.setPublicdatetime(new Timestamp(System.currentTimeMillis()));

            s.beginTransaction();
            s.save(pro);
            String updater = "update ActivityEntity activity set pros = pros+1 where activityid =:aid";
            Query update = s.createQuery(updater);
            update.setParameter("aid",activityID);
            update.executeUpdate();
            s.getTransaction().commit();
        }catch(Exception e){
            s.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return true;
    }
    //tested
    public static boolean cancelPro(String activityID,String userID){
        Session s = null;
        try{
            s = HibernateUtil.getSession();

            ProEntityPK pk = new ProEntityPK();
            pk.setActivityid(activityID);
            pk.setUserid(userID);
            ProEntity fordelete = s.get(ProEntity.class,pk);
            if(fordelete == null)
                return true;

            s.beginTransaction();
            s.delete(fordelete);
            String hql="update ActivityEntity activity set activity.pros = activity.pros-1 where activity.activityid=:aid";
            Query query = s.createQuery(hql);
            query.setParameter("aid",activityID);
            query.executeUpdate();
            s.getTransaction().commit();
        }catch(Exception e){
            s.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return true;
    }
    //tested
    public static boolean publishComment(String activityID,String userID,String content){
        Session s = null;
        try{
            s = HibernateUtil.getSession();
            ActivitycommentEntityPK pk = new ActivitycommentEntityPK();
            pk.setActivityid(activityID);
            pk.setUserid(userID);
            pk.setPublicdatetime(new Timestamp(System.currentTimeMillis()));
            ActivitycommentEntity comment = new ActivitycommentEntity();
            comment.setActivitycommentEntityPK(pk);
            comment.setContent(content);
            s.beginTransaction();
            s.save(comment);
            s.getTransaction().commit();
        }catch(Exception e){
            s.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return true;
    }
    //tested
    public static boolean whetherCanDeleteComment(String userid,String commentPublisher,String activityid){
        if(userid.equals(commentPublisher))
            return true;
        String publisher = null;
        Session s = null;
        try{
            s = HibernateUtil.getSession();
            String hql = "select activity.publisher from ActivityEntity activity where activity.activityid=:aid";
            Query query = s.createQuery(hql);
            query.setParameter("aid",activityid);
            publisher = (String)query.getSingleResult();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        if(publisher.equals(userid))
            return true;
        else
            return false;
    }

    public static boolean deleteActivityComment(ActivitycommentEntityPK pk){
        Session s = null;
        try{
            s = HibernateUtil.getSession();
            ActivitycommentEntity fordelete = s.get(ActivitycommentEntity.class,pk);
            if(fordelete == null)
                return true;

            s.beginTransaction();
            s.delete(fordelete);
            s.getTransaction().commit();
        }catch(Exception e){
            s.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return true;
    }
    //tested
    public static ActivitycommentEntity[] getActivityComments(String activityID){
        Session s = null;
        List resultList = null;
        try{
            s = HibernateUtil.getSession();
            String hql = "from ActivitycommentEntity entity where entity.activitycommentEntityPK.activityid =:aid order by entity.activitycommentEntityPK.publicdatetime asc";
            Query query = s.createQuery(hql);
            query.setParameter("aid",activityID);
            resultList = query.list();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        if(resultList == null)
            return null;
        else{
            ActivitycommentEntity[] entityArray = new ActivitycommentEntity[resultList.size()];
            for(int i=0;i<resultList.size();i++)
                entityArray[i] = (ActivitycommentEntity) resultList.get(i);
            return entityArray;
        }

    }
}