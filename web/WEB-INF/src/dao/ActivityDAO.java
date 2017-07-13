package dao;

import entity.ActivityEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/7/13.

public class ActivityDAO {
    public static ActivityEntity[] getActivities(String lastdate,int number){
        Session s = null;
        try{
            s = HibernateUtil.getSession();
            String hql = "from ActivityEntity as activity where activity.publicdatetime<=:current order by activity.publicdatetime desc";
            Query query = s.createQuery(hql);
            List list = query.list();
            List sublist = list.subList(0,number);

        }
    }
}*/
