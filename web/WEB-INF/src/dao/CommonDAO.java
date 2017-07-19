package dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/14.
 */
public class CommonDAO {
    private CommonDAO(){}
    //tested
    public static boolean deleteItemByPK(Class aClass, Serializable pk){
        Session s = null;
        try{
            s = HibernateUtil.getSession();
            Object entity = s.get(aClass,pk);
            if(entity == null)
                return true;
            s.beginTransaction();
            s.delete(aClass.cast(entity));
            s.getTransaction().commit();
        }catch (Exception e){
            s.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return true;
    }
    //tested
    public static Object getItemByPK(Class aClass, Serializable pk){
        Session s = null;
        Object entity = null;
        try{
            s = HibernateUtil.getSession();
            entity = s.get(aClass,pk);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return entity;
    }
    //tested
    public static boolean saveItem(Object o){
        Session s = null;
        try{
            s = HibernateUtil.getSession();
            s.beginTransaction();
            s.save(o);
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
    //对于SQL设置主键自增的类的update，决不能使用此方法！手动主键自增暂时无问题。记住此方法本质是先删除再插入
    public static boolean updateItem(Class aClass, Serializable pk, Object newInstance){
        Session s = null;
        try{
            s = HibernateUtil.getSession();
            s.beginTransaction();
            Object entity = s.get(aClass,pk);
            s.delete(aClass.cast(entity));
            s.save(newInstance);
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
    public static List queryHql(String hql, Map<String,Object> param){
        Session s = null;
        List resultList = null;
        try{
            s = HibernateUtil.getSession();
            Query query = s.createQuery(hql);
            for(Map.Entry<String,Object> entry:param.entrySet())
                query.setParameter(entry.getKey(), entry.getValue());

            resultList = query.list();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return resultList;
    }
    //tested
    //使用此方法进行删除时，应确保所删除的对象存在，否则返回值与预期不符。可以与上方法配合使用
    public static boolean updateHql(String hql, Map<String,Object> param){
        Session s = null;
        try{
            s = HibernateUtil.getSession();
            Query query = s.createQuery(hql);
            for(Map.Entry<String,Object> entry:param.entrySet())
                query.setParameter(entry.getKey(),entry.getValue());
            s.beginTransaction();
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
}