package dao;

import entity.BlacklistEntity;
import entity.BlacklistEntityPK;
import entity.FriendshipEntity;
import entity.FriendshipEntityPK;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import util.HibernateUtil;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class FriendshipDAO {
    //tested
    public static boolean existFriendship(String user1id,String user2id){
        Session s = null;
        try{
            s = HibernateUtil.getSession();
            FriendshipEntityPK pk1 = new FriendshipEntityPK();
            pk1.setUserid1(user1id);
            pk1.setUserid2(user2id);
            FriendshipEntityPK pk2 = new FriendshipEntityPK();
            pk2.setUserid1(user2id);
            pk2.setUserid2(user1id);
            String hql = "from FriendshipEntity friendship where friendship.friendshipEntityPK=:pk1 or friendship.friendshipEntityPK=:pk2";
            Query query = s.createQuery(hql);
            query.setParameter("pk1",pk1);
            query.setParameter("pk2",pk2);
            List list = query.list();
            if(list.isEmpty())
                return false;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return true;
    }
    //tested
    public static boolean existBlacklistItem(String coaction,String coactee){
        Session s = null;
        try{
            s = HibernateUtil.getSession();
            BlacklistEntityPK blacklistEntityPK = new BlacklistEntityPK();
            blacklistEntityPK.setCoaction(coaction);
            blacklistEntityPK.setCoactee(coactee);
            Criteria criteria = s.createCriteria(BlacklistEntity.class);
            criteria.add(Restrictions.eq("blacklistEntityPK",blacklistEntityPK));
            List list = criteria.list();
            if(list.isEmpty())
                return false;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }
        return true;
    }
    //tested
    public static boolean addBlackListItem(String coaction,String coactee){
        Session s = null;
        try{
            s = HibernateUtil.getSession();
            BlacklistEntityPK pk = new BlacklistEntityPK();
            pk.setCoaction(coaction);
            pk.setCoactee(coactee);
            BlacklistEntity entity = new BlacklistEntity();
            entity.setBlacklistEntityPK(pk);
            entity.setCreatedatetime(new Timestamp(System.currentTimeMillis()));
            s.beginTransaction();
            s.save(entity);
            s.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            s.getTransaction().rollback();
            return false;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return true;
    }
    //tested
    public static boolean addFriendship(String user1id,String user2id){
        Session s = null;
        try{
            s = HibernateUtil.getSession();
            FriendshipEntityPK pk = new FriendshipEntityPK();
            pk.setUserid1(user1id);
            pk.setUserid2(user2id);
            FriendshipEntity entity = new FriendshipEntity();
            entity.setFriendshipEntityPK(pk);
            entity.setFriendshipdatetime(new Timestamp(System.currentTimeMillis()));
            s.beginTransaction();
            s.save(entity);
            s.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            s.getTransaction().rollback();
            return false;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return true;
    }
    //tested
    public static boolean deleteFriendship(String user1id,String user2id){
        if(!existFriendship(user1id,user2id))
            return true;

        Session s = null;
        try{
            s = HibernateUtil.getSession();
            FriendshipEntityPK pk1 = new FriendshipEntityPK();
            pk1.setUserid1(user1id);
            pk1.setUserid2(user2id);
            FriendshipEntityPK pk2 = new FriendshipEntityPK();
            pk2.setUserid1(user2id);
            pk2.setUserid2(user1id);
            FriendshipEntity entity1 = s.get(FriendshipEntity.class,pk1);
            FriendshipEntity entity2 = s.get(FriendshipEntity.class,pk2);
            /*
                非复合主键可以通过session.delete(游离对象)删除
                复合主键则必须先get/load加载再删除
            */
            s.beginTransaction();
            if(entity1 != null)
                s.delete(entity1);
            else
                s.delete(entity2);
            s.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            s.getTransaction().rollback();
            return false;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return true;
    }
    //tested
    public static boolean deleteBlacklistItem(String coaction,String coactee){
        if(!existBlacklistItem(coaction,coactee))
            return true;

        Session s = null;
        try {
            s = HibernateUtil.getSession();
            BlacklistEntityPK pk = new BlacklistEntityPK();
            pk.setCoaction(coaction);
            pk.setCoactee(coactee);

            BlacklistEntity entity = (BlacklistEntity)s.get(BlacklistEntity.class,pk);
            s.beginTransaction();
            s.delete(entity);
            s.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
            s.getTransaction().rollback();
            return false;
        }finally{
            HibernateUtil.safeCloseSession(s);
        }

        return true;
    }
    //tested
    public static List getFriendIDList(String userid){
        Session s = null;
        List resultList = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "from FriendshipEntity friendship where friendship.friendshipEntityPK.userid1=:id or friendship.friendshipEntityPK.userid2=:id";
            Query query = s.createQuery(hql);
            query.setParameter("id", userid);

            List list = query.list();
            resultList = new ArrayList<String>(list.size());
            for (Object o : list) {
                if (((FriendshipEntity) o).getFriendshipEntityPK().getUserid1().equals(userid))
                    resultList.add(((FriendshipEntity) o).getFriendshipEntityPK().getUserid2());
                else
                    resultList.add(((FriendshipEntity) o).getFriendshipEntityPK().getUserid1());
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return resultList;
    }
    //tested
    public static List getBlacklist(String userid){

        Session s = null;
        List resultList = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "from BlacklistEntity blacklist where blacklist.blacklistEntityPK.coaction = :id";
            Query query = s.createQuery(hql);
            query.setParameter("id", userid);

            List list = query.list();
            resultList = new ArrayList<String>(list.size());
            for (Object o : list)
                resultList.add(((BlacklistEntity) o).getBlacklistEntityPK().getCoactee());

        }
        catch(Exception e){
            e.printStackTrace();
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return resultList;
    }
    //tested
    public static boolean setFriendshipNote(String userid,String friendid,String note){
        if(!existFriendship(userid,friendid) && !existFriendship(friendid,userid))
            return false;

        Session s = null;
        Session t = null;
        try{
            FriendshipEntityPK pk1 = new FriendshipEntityPK();
            pk1.setUserid1(userid);
            pk1.setUserid2(friendid);
            FriendshipEntityPK pk2 = new FriendshipEntityPK();
            pk2.setUserid1(friendid);
            pk2.setUserid2(userid);
            s = HibernateUtil.getSession();
            s.beginTransaction();
            String hql = "update FriendshipEntity friendship set friendship.user1Note = :note where friendship.friendshipEntityPK = :pk";
            Query query = s.createQuery(hql);
            query.setParameter("note",note);
            query.setParameter("pk",pk1);
            query.executeUpdate();
            s.getTransaction().commit();

            t = HibernateUtil.getSession();
            t.beginTransaction();
            String hql2 = "update FriendshipEntity friendship set friendship.user2Note = :note where friendship.friendshipEntityPK = :pk";
            Query query2 = t.createQuery(hql2);
            query2.setParameter("note",note);
            query2.setParameter("pk",pk2);
            query2.executeUpdate();
            t.getTransaction().commit();
        }catch(Exception e){
            s.getTransaction().rollback();
            t.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.safeCloseSession(s);
            HibernateUtil.safeCloseSession(t);
        }

        return true;
    }
}