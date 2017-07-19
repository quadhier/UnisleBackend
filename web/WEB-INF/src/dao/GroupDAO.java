package dao;

import entity.ActivityEntity;
import entity.GroupmemberEntity;
import entity.GroupmemberEntityPK;
import entity.UgroupEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/19.
 */
public class GroupDAO {
    private GroupDAO(){}

    public static String createGroup(String userid,String name,String tag,
                                     String school,String department,String description,byte[] pic){
        Session s = null;
        Session c = null;
        String newGroupID = null;
        try{
            c = HibernateUtil.getSession();
            Criteria counter = c.createCriteria(UgroupEntity.class);
            counter.setProjection(Projections.max("groupid"));
            if(counter.uniqueResult()==null)
                newGroupID="30100000001";
            else {
                long result = Long.parseLong((String) counter.uniqueResult());
                newGroupID = String.valueOf(result + 1);
            }

            s = HibernateUtil.getSession();
            UgroupEntity group = new UgroupEntity();
            group.setGroupid(newGroupID);
            group.setDirector(userid);
            group.setCreatedatetime(new Timestamp(System.currentTimeMillis()));
            group.setName(name);
            if(tag!=null) group.setTag(tag);
            if(school!=null) group.setSchool(school);
            if(department!=null) group.setDepartment(department);
            if (description!=null) group.setDescription(description);
            if(pic!=null) group.setGrouppic(pic);

            GroupmemberEntityPK pk = new GroupmemberEntityPK();
            pk.setUserid(userid);
            pk.setGroupid(newGroupID);
            GroupmemberEntity groupmemberEntity = new GroupmemberEntity();
            groupmemberEntity.setPosition("director");
            groupmemberEntity.setVisibility("yes");
            groupmemberEntity.setGroupmemberEntityPK(pk);
            groupmemberEntity.setJoindatetime(new Timestamp(System.currentTimeMillis()));

            s.beginTransaction();
            s.save(group);
            s.save(groupmemberEntity);
            s.getTransaction().commit();
        }catch(Exception e){
            s.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }finally {
            HibernateUtil.safeCloseSession(s);
            HibernateUtil.safeCloseSession(c);
        }

        return newGroupID;
    }

    public static String getPositionInGroup(String userid,String groupid){
        GroupmemberEntityPK pk = new GroupmemberEntityPK();
        pk.setUserid(userid);
        pk.setGroupid(groupid);
        GroupmemberEntity resultEntity = (GroupmemberEntity)CommonDAO.getItemByPK(GroupmemberEntity.class,pk);
        if(resultEntity == null)
            return null;

        String result = resultEntity.getPosition();
        return result;
    }

    public static String getDirectorID(String groupid){
        return ((UgroupEntity)CommonDAO.getItemByPK(UgroupEntity.class,groupid)).getDirector();
    }
    //untested
    public static boolean dismissGroup(String groupid){
        if(CommonDAO.getItemByPK(UgroupEntity.class,groupid) == null)
            return true;

        Session s = null;
        try{
            s = HibernateUtil.getSession();
            UgroupEntity forDelete = s.get(UgroupEntity.class,groupid);
            String hql = "delete GroupmemberEntity gm where gm.groupmemberEntityPK.groupid =:gid";
            Query query = s.createQuery(hql);
            query.setParameter("gid",groupid);

            s.beginTransaction();
            s.delete(forDelete);
            query.executeUpdate();
            s.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            s.getTransaction().rollback();
            return false;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return true;
    }

    public static boolean isInGroup(String userid,String groupid){
        GroupmemberEntityPK pk = new GroupmemberEntityPK();
        pk.setGroupid(groupid);
        pk.setUserid(userid);

        Object o = CommonDAO.getItemByPK(GroupmemberEntity.class,pk);

        if(o == null)
            return false;

        return true;
    }

    public static boolean addMember(String groupid,String userid){
        GroupmemberEntityPK pk = new GroupmemberEntityPK();
        pk.setGroupid(groupid);
        pk.setUserid(userid);
        GroupmemberEntity entity = new GroupmemberEntity();
        entity.setGroupmemberEntityPK(pk);
        entity.setJoindatetime(new Timestamp(System.currentTimeMillis()));
        entity.setVisibility("yes");
        entity.setPosition("member");

        return CommonDAO.saveItem(entity);
    }

    public static List getAllMembersID(String groupid){
        String hql = "select entity.groupmemberEntityPK.userid from GroupmemberEntity entity where entity.groupmemberEntityPK.groupid = :gid";
        Map<String,Object> params = new HashMap<>();
        params.put("gid",groupid);

        return CommonDAO.queryHql(hql,params);
    }

    public static boolean alterPositionInGroup(String groupid,String alteeid,String newPositon){
        GroupmemberEntityPK pk = new GroupmemberEntityPK();
        pk.setUserid(alteeid);
        pk.setGroupid(groupid);
        GroupmemberEntity oldEntity = (GroupmemberEntity) CommonDAO.getItemByPK(GroupmemberEntity.class,pk);
        if(oldEntity==null)
            return false;
        oldEntity.setPosition(newPositon);

        return CommonDAO.updateItem(GroupmemberEntity.class,pk,oldEntity);
    }

    public static boolean alterVisibility(String memberid,String groupid,String newVisibility){
        GroupmemberEntityPK pk = new GroupmemberEntityPK();
        pk.setUserid(memberid);
        pk.setGroupid(groupid);
        GroupmemberEntity oldEntity = (GroupmemberEntity) CommonDAO.getItemByPK(GroupmemberEntity.class,pk);
        if(oldEntity==null)
            return false;
        oldEntity.setVisibility(newVisibility);

        return CommonDAO.updateItem(GroupmemberEntity.class,pk,oldEntity);
    }

    public static boolean kickoutMember(String groupid,String memberid){
        GroupmemberEntityPK pk = new GroupmemberEntityPK();
        pk.setGroupid(groupid);
        pk.setUserid(memberid);
        if(CommonDAO.getItemByPK(GroupmemberEntity.class,pk)==null)
            return true;

        return CommonDAO.deleteItemByPK(GroupmemberEntity.class,pk);
    }
    //untested
    public static List searchGroupByName(String name){
        String hql = "from UgroupEntity group where group.name like :gname";
        Map<String, Object> params = new HashMap<>();
        params.put("gname","%"+name+"%");

        return CommonDAO.queryHql(hql,params);
    }
    //untested
    public static List searchGroupByTag(String partOfTag){
        String hql = "from UgroupEntity group where group.tag like :gtag";
        Map<String, Object> params = new HashMap<>();
        params.put("gtag","%"+partOfTag+"%");

        return CommonDAO.queryHql(hql,params);
    }

    public static boolean changeDirector(String groupid,String newDirector){
        //有缺陷：出现bug时不能回滚
        String oldDirector = ((UgroupEntity)CommonDAO.getItemByPK(UgroupEntity.class,groupid)).getDirector();
        GroupmemberEntityPK oldpk = new GroupmemberEntityPK();
        oldpk.setUserid(oldDirector);
        oldpk.setGroupid(groupid);
        GroupmemberEntity oldDirectorEntity = (GroupmemberEntity)CommonDAO.getItemByPK(GroupmemberEntity.class,oldpk);
        if(oldDirectorEntity == null)
            return false;
        oldDirectorEntity.setPosition("manager");
        if(!CommonDAO.updateItem(GroupmemberEntity.class,oldpk,oldDirectorEntity))
            return false;

        GroupmemberEntityPK pk = new GroupmemberEntityPK();
        pk.setUserid(newDirector);
        pk.setGroupid(groupid);
        GroupmemberEntity oldMemberEntity = (GroupmemberEntity)CommonDAO.getItemByPK(GroupmemberEntity.class,pk);
        if(oldMemberEntity == null)
            return false;

        oldMemberEntity.setPosition("director");
        UgroupEntity older = (UgroupEntity) CommonDAO.getItemByPK(UgroupEntity.class,groupid);
        older.setDirector(newDirector);

        return CommonDAO.updateItem(UgroupEntity.class,groupid,older) && CommonDAO.updateItem(GroupmemberEntity.class,pk,oldMemberEntity);
    }
}