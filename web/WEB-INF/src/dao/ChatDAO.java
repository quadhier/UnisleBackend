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

    public static boolean sendMessage(String sender,String receiver,String content){
        ChatrecordEntityPK pk = new ChatrecordEntityPK();
        pk.setSender(sender);
        pk.setReceiver(receiver);
        pk.setSenddatedtime(new Timestamp(System.currentTimeMillis()));
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
}