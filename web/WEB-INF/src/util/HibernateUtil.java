package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

/**
 * Created by Administrator on 2017/7/12.
 */
public class HibernateUtil {
    private static SessionFactory sf;

    //禁止创建该类的实例
    private HibernateUtil(){}

    public static synchronized SessionFactory getSessionFactroy(){
        if(sf == null){
            Configuration cfg = new Configuration();
            File file = new File("G:\\GitProjects\\UnisleBackend\\web\\WEB-INF\\hibernate.cfg.xml");
            cfg.configure(file);
            sf = cfg.buildSessionFactory();
        }

        return sf;
    }
    //该方法只应当在服务器停止运行时调用一次
    public static void closeSessionFactory(){
        if(sf != null)
            sf.close();
    }

    public static Session getSession(){
        return getSessionFactroy().openSession();
    }

    public static void safeCloseSession(Session s){
        if(s != null)
            s.close();
    }
}
