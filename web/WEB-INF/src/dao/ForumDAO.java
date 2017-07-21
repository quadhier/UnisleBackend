package dao;

import entity.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import util.ControllerUtil;
import util.HibernateUtil;
import util.Rewrapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForumDAO {
    private ForumDAO(){}
    public static final int EXPS_PER_LEVEL = 2000;

    public static boolean addTheme(String themename,String boardname){
        String hql = "select distinct b.boardname from BoardEntity b";
        List boardnameList = CommonDAO.queryHql(hql,null);
        if(boardnameList==null || !boardnameList.contains(boardname) || existTheme(themename,boardname))
            return false;
        ThemeEntityPK pk = new ThemeEntityPK();
        pk.setBoardname(boardname);
        pk.setThemename(themename);
        ThemeEntity theme = new ThemeEntity();
        theme.setThemeEntityPK(pk);

        return CommonDAO.saveItem(theme);
    }

    public static boolean existTheme(String themename,String boardname){
        ThemeEntityPK pk = new ThemeEntityPK();
        pk.setBoardname(boardname);
        pk.setThemename(themename);
        if(CommonDAO.getItemByPK(ThemeEntity.class,pk) == null)
            return false;
        else
            return true;
    }

    public static boolean deleteTheme(String themename,String boardname){
        if(!existTheme(themename, boardname))
            return true;

        ThemeEntityPK pk = new ThemeEntityPK();
        pk.setBoardname(boardname);
        pk.setThemename(themename);

        return CommonDAO.deleteItemByPK(ThemeEntity.class,pk);
    }

    public static boolean alterThemeName(String oldthemename,String newthiemename,String boardname){
        if(!existTheme(oldthemename,boardname))
            return false;
        ThemeEntityPK pk = new ThemeEntityPK();
        pk.setBoardname(boardname);
        pk.setThemename(oldthemename);
        ThemeEntity oldEntity = (ThemeEntity)CommonDAO.getItemByPK(ThemeEntity.class,pk);
        if(oldEntity == null)
            return false;
        ThemeEntityPK newpk = new ThemeEntityPK();
        newpk.setThemename(newthiemename);
        newpk.setBoardname(boardname);
        oldEntity.setThemeEntityPK(newpk);
        return CommonDAO.updateItem(ThemeEntity.class,pk,oldEntity);
    }

    public static boolean calculateExp(String userid,int exps){
        ForumaccountEntity entity = (ForumaccountEntity) CommonDAO.getItemByPK(ForumaccountEntity.class,userid);
        if(entity==null || exps <= 0)
            return false;

        int totalExp = exps + entity.getExp();
        int oldRank = entity.getRank();
        int levelsAdd = totalExp/EXPS_PER_LEVEL;
        int expsLeft = totalExp%EXPS_PER_LEVEL;
        entity.setRank(oldRank+levelsAdd);
        entity.setExp(expsLeft);
        return CommonDAO.updateItem(ForumaccountEntity.class,userid,entity);
    }

    public static String publishArticle(String authorid,String themename,
                                        String boardname,String title,String content,String commentallowed){
        Session s = null;
        String newArticleID = null;
        try{
            s = HibernateUtil.getSession();
            Criteria counter = s.createCriteria(ArticleEntity.class);
            counter.setProjection(Projections.max("articleid"));
            if(counter.uniqueResult() == null)
                newArticleID = "60000000001";
            else
                newArticleID = String.valueOf(Long.parseLong((String)counter.uniqueResult()) + 1);
            HibernateUtil.safeCloseSession(s);
            s = HibernateUtil.getSession();
            ArticleEntity entity = new ArticleEntity();
            entity.setArticleid(newArticleID);
            entity.setAuthor(authorid);
            entity.setBoardname(boardname);
            entity.setThemename(themename);
            entity.setTitle(title);
            entity.setContent(content);
            entity.setVisibility("all");
            entity.setViewtimes(Short.valueOf("1"));
            Timestamp current = new Timestamp(System.currentTimeMillis());
            entity.setPublicdatetime(current);
            entity.setLastmoddatetime(current);
            entity.setLastcomdatetime(current);
            if(commentallowed == null)
                entity.setCommentallowed("yes");
            else if(commentallowed.equals("yes") || commentallowed.equals("no"))
                entity.setCommentallowed(commentallowed);
            else
                entity.setCommentallowed("yes");
            s.beginTransaction();
            s.save(entity);
            s.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            s.getTransaction().rollback();
            return null;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return newArticleID;
    }

    public static boolean alterArticleContent(String articleid,String newContent){
        ArticleEntity entity = (ArticleEntity)CommonDAO.getItemByPK(ArticleEntity.class,articleid);
        if(entity == null)
            return false;
        entity.setContent(newContent);
        entity.setLastmoddatetime(new Timestamp(System.currentTimeMillis()));
        return CommonDAO.updateItem(ArticleEntity.class,articleid,entity);
    }

    public static boolean setVisibilityAndCommentallowed(String articleid,
                                                         String newVisibility,String newCommentallowed){
        ArticleEntity entity = (ArticleEntity)CommonDAO.getItemByPK(ArticleEntity.class,articleid);
        if(entity == null)
            return false;
        if(newVisibility != null && (newVisibility.equals("self") || newVisibility.equals("all")))
            entity.setVisibility(newVisibility);
        if(newCommentallowed!=null && (newCommentallowed.equals("yes") || newCommentallowed.equals("no")))
            entity.setCommentallowed(newCommentallowed);

        return CommonDAO.updateItem(ArticleEntity.class,articleid,entity);
    }

    /**
     * @return List中每个元素是一个Map<String,Object>，对应articleid,author,commentallowed
     * ,三个时间，title,viewtimes
     */
    public static List getArticleList(String userid,String boardname,
                                      String themename,Timestamp thisTimeBefore,int startat,int number) {
        Session s = null;
        List result = null;
        List wrappedList = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "from ArticleEntity entity where entity.boardname = :bdnm and entity.themename = :tmnm " +
                    "and entity.lastcomdatetime<= :time and (entity.visibility = 'all' or entity.author=:uid) order by entity.lastcomdatetime desc";
            Query query = s.createQuery(hql);
            query.setParameter("bdnm",boardname);
            query.setParameter("tmnm",themename);
            query.setParameter("time",thisTimeBefore);
            query.setParameter("uid",userid);
            query.setFirstResult(startat);
            query.setMaxResults(number);
            result = query.list();
            wrappedList = Rewrapper.wrapList(result,ArticleEntity.class,"111001111001");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return wrappedList;
    }
    /**
     * @return List中每个元素是一个Map<String,Object>，对应boardname,themename,articleid,commentallowed,
     * 三个时间，title,viewtimes
     */
    public static List getMyArticleList(String userid,int startat,int numbers){
        Session s = null;
        List result = null;
        List wrappedList = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "from ArticleEntity entity where entity.author = :uid order by entity.lastcomdatetime desc";
            Query query = s.createQuery(hql);
            query.setParameter("uid",userid);
            query.setFirstResult(startat);
            query.setMaxResults(numbers);
            result = query.list();
            wrappedList = Rewrapper.wrapList(result,ArticleEntity.class,"110111111011");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }

        return wrappedList;
    }

    public static String readArticleContent(String userid,String articleid){
        ArticleEntity article = (ArticleEntity)CommonDAO.getItemByPK(ArticleEntity.class,articleid);
        if(article == null)
            return null;
        String hql = "update ArticleEntity e set e.viewtimes = e.viewtimes+1 where e.articleid = :aid";
        Map params = new HashMap();
        params.put("aid",articleid);
        if(!CommonDAO.updateHql(hql,params))
            return null;
        ViewrecordEntityPK pk = new ViewrecordEntityPK();
        pk.setArticleid(articleid);
        pk.setUserid(userid);
        pk.setViewdatetime(new Timestamp(System.currentTimeMillis()));
        ViewrecordEntity entity = new ViewrecordEntity();
        entity.setViewrecordEntityPK(pk);
        if(!CommonDAO.saveItem(entity))
            return null;

        return article.getContent();
    }

    public static boolean deleteArticle(String articleid){
        return CommonDAO.deleteItemByPK(ArticleEntity.class,articleid);
    }

    public static boolean isCommentAllowed(String articleid){
        ArticleEntity article = (ArticleEntity)CommonDAO.getItemByPK(ArticleEntity.class,articleid);
        if(article == null)
            return false;
        boolean isComment = true;
        if(article.getCommentallowed().equals("no"))
            isComment = false;

        return isComment;
    }

    public static boolean publishArticleComment(String userid,String articleid,String content){
        Session s = null;
        Timestamp rightnow = new Timestamp(System.currentTimeMillis());
        try{
            s = HibernateUtil.getSession();
            String updatehql = "update ArticleEntity entity set entity.lastcomdatetime = :current where entity.articleid = :aid";
            Query query = s.createQuery(updatehql);
            query.setParameter("current",rightnow);
            query.setParameter("aid",articleid);

            ArticlecommentEntityPK pk = new ArticlecommentEntityPK();
            pk.setArticleid(articleid);
            pk.setUserid(userid);
            pk.setPublicdatetime(rightnow);
            ArticlecommentEntity entity = new ArticlecommentEntity();
            entity.setArticlecommentEntityPK(pk);
            entity.setContent(content);

            s.beginTransaction();
            query.executeUpdate();
            s.save(entity);
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

    public static boolean saveEditingWords(String userid,String type,String content){
        Timestamp current = new Timestamp(System.currentTimeMillis());
        EditrecordEntityPK pk = new EditrecordEntityPK();
        pk.setType(type);
        pk.setUserid(userid);
        EditrecordEntity oldentity = (EditrecordEntity)CommonDAO.getItemByPK(EditrecordEntity.class,pk);
        if(oldentity != null){
            oldentity.setContent(content);
            oldentity.setEditdatetime(current);
            return CommonDAO.updateItem(EditrecordEntity.class,pk,oldentity);
        }else{
            EditrecordEntity newentity = new EditrecordEntity();
            newentity.setEditrecordEntityPK(pk);
            newentity.setContent(content);
            newentity.setEditdatetime(current);
            return CommonDAO.saveItem(newentity);
        }
    }

    public static String getEditingWords(String userid,String type){
        EditrecordEntityPK pk = new EditrecordEntityPK();
        pk.setType(type);
        pk.setUserid(userid);
        EditrecordEntity entity = (EditrecordEntity)CommonDAO.getItemByPK(EditrecordEntity.class,pk);
        if(entity == null)
            return null;
        return entity.getContent();
    }

    public static boolean whetherCanDeleteComment(String userid,String commentauthorid,String articleid){
        if(userid.equals(commentauthorid))
            return true;
        ArticleEntity entity = (ArticleEntity) CommonDAO.getItemByPK(ArticleEntity.class,articleid);
        if(entity == null)
            return false;
        String authorid = entity.getAuthor();
        if(authorid.equals(userid))
            return true;
        if(getPrivilige(userid) >= 4)
            return true;
        return false;
    }

    public static List getArticleCommentList(String articleid){
        String hql = "from ArticlecommentEntity entity where entity.articlecommentEntityPK.articleid =:aid";
        Map<String,Object> params = new HashMap<>();
        params.put("aid",articleid);
        return CommonDAO.queryHql(hql,params);
    }

    public static boolean addToCollection(String userid,String articleid){
        CollectionEntityPK pk = new CollectionEntityPK();
        pk.setArticleid(articleid);
        pk.setUserid(userid);
        Object test = CommonDAO.getItemByPK(CollectionEntity.class,pk);
        if(test != null)
            return true;
        CollectionEntity entity = new CollectionEntity();
        entity.setCollectionEntityPK(pk);
        entity.setCollectdatetime(new Timestamp(System.currentTimeMillis()));
        return CommonDAO.saveItem(entity);
    }

    public static boolean deleteFromCollection(String userid,String articleid){
        CollectionEntityPK pk = new CollectionEntityPK();
        pk.setArticleid(articleid);
        pk.setUserid(userid);
        return CommonDAO.deleteItemByPK(CollectionEntity.class,pk);
    }

    //查看时按照收藏时间选择查看条数
    //返回对象格式与文章列表相同,加上版块和栏目信息以及收藏时间
    public static List getCollectionList(String userid,int startat,int number){
        Session s = null;
        List result = null;
        List wrappedResult = null;
        try {
            s = HibernateUtil.getSession();
            String getArticlePKList = "from CollectionEntity entity where entity.collectionEntityPK.userid = :uid order by entity.collectdatetime desc";
            Query query = s.createQuery(getArticlePKList);
            query.setParameter("uid",userid);
            query.setFirstResult(startat);
            query.setMaxResults(number);
            List articleList = query.list();
            if (articleList == null || articleList.isEmpty())
                return null;

            List articleIDList = new ArrayList();
            List<String> orderID = new ArrayList<>(articleList.size());
            List<Timestamp> orderTime = new ArrayList<>(articleList.size());
            int i=0;
            for(Object o:articleList) {
                articleIDList.add(((CollectionEntity) o).getCollectionEntityPK().getArticleid());
                orderID.add(i,((CollectionEntity) o).getCollectionEntityPK().getArticleid());
                orderTime.add(i,((CollectionEntity) o).getCollectdatetime());
                i++;
            }

            String hql = "from ArticleEntity entity where entity.articleid in :aidlist";
            Query getEntity = s.createQuery(hql);
            getEntity.setParameter("aidlist",articleIDList);
            result = getEntity.list();
            if(result == null || result.isEmpty())
                return null;

            Map[] maps = new Map[result.size()];
            for(int j=0;j<result.size();j++){
                ArticleEntity entity = (ArticleEntity) result.get(j);
                Map adepter = Rewrapper.wrap(entity,"111111111011");
                int index = orderID.indexOf(entity.getArticleid());
                adepter.put("collectdatetime",orderTime.get(index));
                //注意指定位置插入不能插到空位置。只能接到满位置的后一个或者替代一个元素。
                maps[index]=adepter;
            }
            wrappedResult = ControllerUtil.arrToList(maps);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }
        return wrappedResult;
    }

    //返回格式与文章列表相同，每个文章只有一条最近浏览的记录,按浏览时间逆序排列
    public static List getViewHistory(String userid,Timestamp lasttime,int startat,int number){
        Session s = null;
        List result = null;
        List wrappedResult = null;
        try {
            s = HibernateUtil.getSession();
            String sql = "select aid,vtime,author,boardname,themename,title,viewtimes,lastcomdatetime " +
                    "from  " +
                    "  (select articleid as aid,max(viewdatetime) as vtime" +
                    "  from viewrecord" +
                    "  where userid = :uid " +" and viewdatetime <= :mtime " +
                    "  group by aid " +
                    "  order by vtime desc) as vrecord join article on vrecord.aid = article.articleid";
            //注意！这里如果外层查询用order by时间来排序的话，就会导致在setMaxResults()的时候发生冲突导致错误。
            Query query = s.createSQLQuery(sql);
            query.setParameter("mtime",lasttime);
            query.setParameter("uid",userid);
            query.setFirstResult(startat);
            query.setMaxResults(number);

            result = query.list();
            if(result == null || result.isEmpty())
                return null;
            wrappedResult = new ArrayList();
            for(Object o:result){
                Object[] oarray = (Object[])o;
                Map adapter = new HashMap();
                adapter.put("articleid",oarray[0]);
                adapter.put("viewdatetime",oarray[1]);
                adapter.put("author",oarray[2]);
                adapter.put("boardname",oarray[3]);
                adapter.put("themename",oarray[4]);
                adapter.put("title",oarray[5]);
                adapter.put("viewtimes",oarray[6]);
                adapter.put("lastcomdatetime",oarray[7]);
                wrappedResult.add(adapter);
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }
        return wrappedResult;
    }

    public static boolean clearViewHistory(String userid){
        String hql = "delete ViewrecordEntity record where record.viewrecordEntityPK.userid = :uid";
        Map params = new HashMap();
        params.put("uid",userid);

        return CommonDAO.updateHql(hql,params);
    }

    public static int getPrivilige(String userid){
        ForumaccountEntity entity = (ForumaccountEntity)CommonDAO.getItemByPK(ForumaccountEntity.class,userid);
        if(entity == null)
            return -1;

        return entity.getPrivilige();
    }

    public static boolean alterPrivilige(String userid,int newPrivilige){
        ForumaccountEntity entity = (ForumaccountEntity)CommonDAO.getItemByPK(ForumaccountEntity.class,userid);
        if(entity == null)
            return false;

        entity.setPrivilige(newPrivilige);
        return CommonDAO.updateItem(ForumaccountEntity.class,userid,entity);
    }

    //包装后的map包含：用户id、头像、昵称、性别、个性签名、学校、专业，用户论坛等级和经验，用户权限，
    public static Map getWrappedForumAccountInfo(String userid){
        Map wrapper = new HashMap();
        UuserEntity user = (UuserEntity)CommonDAO.getItemByPK(UuserEntity.class,userid);
        if(user == null)
            return null;
        ForumaccountEntity forum = (ForumaccountEntity)CommonDAO.getItemByPK(ForumaccountEntity.class,userid);
        wrapper.put("userid",user.getUserid());
        wrapper.put("userpic",user.getUserpic());
        wrapper.put("sex",user.getSex());
        wrapper.put("nickname",user.getNickname());
        wrapper.put("signature",user.getSignature());
        wrapper.put("school",user.getSchool());
        wrapper.put("department",user.getDepartment());
        wrapper.put("rank",forum.getRank());
        wrapper.put("exp",forum.getExp());
        wrapper.put("privilige",forum.getPrivilige());
        return wrapper;
    }
}
/*

            String getArticleIDList = "select subtable.articleid,article.title from (select distinct entity.viewrecordEntityPK.articleid as articleid,max(entity.viewrecordEntityPK.viewdatetime) as latesttime " +
                    "from ViewrecordEntity entity where entity.viewrecordEntityPK.userid = :uid " +
                    "and entity.viewrecordEntityPK.viewdatetime<=:time group by entity.viewrecordEntityPK.articleid " +
                    "order by max(entity.viewrecordEntityPK.viewdatetime) desc as subtable) join ArticleEntity article on subtable.articleid = article.articleid";
            Query query = s.createQuery(getArticleIDList);
            query.setParameter("uid",userid);
            query.setParameter("time",lasttime);
            query.setFirstResult(startat);
            query.setMaxResults(number);
            List articleIDList = query.list();
            if (articleIDList == null || articleIDList.isEmpty())
                return null;

            String hql = "from ArticleEntity entity where entity.articleid in :aidlist";
            Query getEntity = s.createQuery(hql);
            getEntity.setParameter("aidlist",articleIDList);
            result = getEntity.list();
            if(result == null || result.isEmpty())
                return null;

            wrappedResult = Rewrapper.wrapList(result,ArticleEntity.class,"111111111011");
 */