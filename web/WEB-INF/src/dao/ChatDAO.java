package dao;

import entity.ChatrecordEntity;
import entity.ChatrecordEntityPK;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatDAO {
    private ChatDAO(){}

    public static boolean sendMessage(String sender,String receiver,String content,Timestamp sendtime){
        ChatrecordEntityPK pk = new ChatrecordEntityPK();
        pk.setSender(sender);
        pk.setReceiver(receiver);
        pk.setSenddatedtime(sendtime);
        ChatrecordEntity record = new ChatrecordEntity();
        record.setChatrecordEntityPK(pk);
        record.setState("unread");
        record.setContent(content);

        return CommonDAO.saveItem(record);
    }

    public static boolean setMessageRead(ChatrecordEntityPK pk){
        ChatrecordEntity entity = (ChatrecordEntity)CommonDAO.getItemByPK(ChatrecordEntity.class,pk);
        if(entity == null)
            return false;
        entity.setState("read");
        return CommonDAO.updateItem(ChatrecordEntity.class,pk,entity);
    }

    public static List<ChatrecordEntity> getMessageList(String user1, String user2, Timestamp lasttime, int startat, int number){
        Session s = null;
        List resultList = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "from ChatrecordEntity record where " +
                    "((record.chatrecordEntityPK.sender =:uid1 and record.chatrecordEntityPK.receiver =:uid2) " +
                    "or (record.chatrecordEntityPK.sender =:uid2 and record.chatrecordEntityPK.receiver =:uid1)) " +
                    "and record.chatrecordEntityPK.senddatedtime <=:current";
            Query query = s.createQuery(hql);
            query.setParameter("uid1", user1);
            query.setParameter("uid2", user2);
            query.setParameter("current", lasttime);
            query.setFirstResult(startat);
            query.setMaxResults(number);

            resultList = query.list();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return resultList;
    }

    public static List<ChatrecordEntity> getUnreadMessageList(String user,int startat,int number){
        Session s = null;
        List resultList = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "from ChatrecordEntity record where record.chatrecordEntityPK.receiver =:uid and record.state = 'unread'" +
                    "order by record.chatrecordEntityPK.senddatedtime asc";
            Query query = s.createQuery(hql);
            query.setParameter("uid", user);
            query.setFirstResult(startat);
            query.setMaxResults(number);

            resultList = query.list();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return resultList;
    }

    public static int gerUnreadMessageNumber(String receiverid,String senderid){
        Session s = null;
        int unread = -1;
        try {
            s = HibernateUtil.getSession();
            String hql = "select count(*) from ChatrecordEntity record where record.chatrecordEntityPK.sender = :sdr " +
                    "and record.chatrecordEntityPK.receiver =:rcvr and record.state='unread'";
            Query query = s.createQuery(hql);
            query.setParameter("sdr",senderid);
            query.setParameter("rcvr",receiverid);
            unread = Integer.valueOf(query.uniqueResult().toString());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return unread;
    }

    public static List<String> getUnreadMessageSender(String receiverid){
        String hql = "select distinct record.sender from ChatrecordEntity record where  " +
                "record.chatrecordEntityPK.receiver =:rcvr and record.state='unread'";
        Map params = new HashMap();
        params.put("rcvr",receiverid);

        return CommonDAO.queryHql(hql,params);
    }

    public static boolean deleteSomeoneMessage(String user1,String user2){
        String hql = "delete ChatrecordEntity e where ((e.chatrecordEntityPK.sender =:uid1 and e.chatrecordEntityPK.receiver =:uid2) " +
        " or (e.chatrecordEntityPK.sender =:uid2 and e.chatrecordEntityPK.receiver =:uid1))";
        Map params = new HashMap();
        params.put("uid1",user1);
        params.put("uid2",user2);

        return CommonDAO.updateHql(hql,params);
    }
}