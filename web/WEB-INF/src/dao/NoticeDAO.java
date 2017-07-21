package dao;

import entity.NoticeEntity;
import entity.NoticeEntityPK;
import entity.UuserEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NoticeDAO {
    private NoticeDAO() {}
    //tested
    public static boolean sendNotice(String sender, String receiver, String content, String type) {
        NoticeEntityPK pk = new NoticeEntityPK();
        pk.setSender(sender);
        pk.setReceiver(receiver);
        pk.setGendatetime(new Timestamp(System.currentTimeMillis()));
        NoticeEntity newNotice = new NoticeEntity();
        newNotice.setNoticeEntityPK(pk);
        newNotice.setContent(content);
        newNotice.setType(type);
        newNotice.setStatus("unread");

        return CommonDAO.saveItem(newNotice) && setNoticenumPlusOne(receiver);
    }
    //tested
    public static boolean seekSendedNotice(String sender, String receiver, String type) {
        Session s = null;
        List list = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "from NoticeEntity entity where entity.noticeEntityPK.sender =:sd and entity.noticeEntityPK.receiver = :rc and entity.type = :tp";
            Query query = s.createQuery(hql);
            query.setParameter("sd", sender);
            query.setParameter("rc", receiver);
            query.setParameter("tp", type);
            list = query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            HibernateUtil.safeCloseSession(s);
        }

        if (list.isEmpty())
            return false;
        else
            return true;
    }
    //tested
    //type字段可以为Null。如果为null，表示获得所有消息
    public static NoticeEntity[] getAllNoticeList(String receiver, String type) {
        String hql = "from NoticeEntity entity where entity.noticeEntityPK.receiver=:rc and entity.type =:tp order by entity.noticeEntityPK.gendatetime desc";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("rc", receiver);
        params.put("tp", type);
        List list = CommonDAO.queryHql(hql, params);
        if (list == null)
            return null;

        NoticeEntity[] result = new NoticeEntity[list.size()];
        for (int i = 0; i < list.size(); i++)
            result[i] = (NoticeEntity) list.get(i);

        return result;
    }
    //tested
    //type字段可以为Null。如果为null，表示获得所有消息
    public static NoticeEntity[] getNewNoticeList(String receiver, String type) {
        String hql = "from NoticeEntity n where n.noticeEntityPK.receiver=:rc and n.type =:tp and n.status = 'unread' order by n.noticeEntityPK.gendatetime desc";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("rc", receiver);
        params.put("tp", type);
        List list = CommonDAO.queryHql(hql, params);
        if (list == null)
            return null;

        NoticeEntity[] result = new NoticeEntity[list.size()];
        for (int i = 0; i < list.size(); i++)
            result[i] = (NoticeEntity) list.get(i);

        return result;
    }

    //通过主键删除一条消息
    public static boolean deleteANotice(NoticeEntityPK pk) {
        return CommonDAO.deleteItemByPK(NoticeEntity.class, pk);
    }
    //tested
    //此方法可以用于删除拉黑用户对你发送的所有消息
    public static boolean deleteSomebodyNotice(String sender, String receiver) {
        String existhql = "from NoticeEntity n where n.noticeEntityPK.sender = :sd and n.noticeEntityPK.receiver=:rc";
        String deletehql = "delete " + existhql;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sd", sender);
        param.put("rc", receiver);
        if (CommonDAO.queryHql(existhql, param) == null)
            return true;

        return CommonDAO.updateHql(deletehql, param);
    }
    //tested
    //清空某一类型的消息列表
    public static boolean deleteAKindNotice(String user, String type) {
        String existhql = "from NoticeEntity n where n.noticeEntityPK.receiver=:rc and n.type = :tp";
        String deletehql = "delete " + existhql;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tp", type);
        param.put("rc", user);
        if (CommonDAO.queryHql(existhql, param) == null)
            return true;

        return CommonDAO.updateHql(deletehql, param);
    }
    //tested
    public static boolean setNoticeRead(NoticeEntityPK pk) {
        NoticeEntity original = (NoticeEntity) CommonDAO.getItemByPK(NoticeEntity.class, pk);
        original.setStatus("read");
        return CommonDAO.updateItem(NoticeEntity.class, pk, original) && setNoticenumMinus(pk.getReceiver(),1);
    }

    public static boolean setNoticenumPlusOne(String userid){
        String hql = "update UuserEntity user set user.numnitice = user.numnotice+1 where user.userid = :uid";
        Map params = new HashMap();
        params.put("uid",userid);

        return CommonDAO.updateHql(hql,params);
    }

    public static boolean setNoticenumMinus(String userid,int absOfMinus){
        int originNum = getNoticenum(userid);
        if(originNum<0)
            return false;
        originNum = originNum-absOfMinus>0?originNum-absOfMinus:0;

        String hql = "update UuserEntity user set user.numnitice = :nnum where user.userid = :uid";
        Map params = new HashMap();
        params.put("uid",userid);
        params.put("nnum",originNum);

        return CommonDAO.updateHql(hql,params);
    }

    public static int getNoticenum(String userid){
        UuserEntity user = (UuserEntity)CommonDAO.getItemByPK(UuserEntity.class,userid);
        if(user == null)
            return -1;

        return user.getNumnotice();
    }
}