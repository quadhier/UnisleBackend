package dao;

import org.hibernate.Session;
import util.HibernateUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/14.
 */
public class CommonDAO {
    private CommonDAO(){}

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
}
