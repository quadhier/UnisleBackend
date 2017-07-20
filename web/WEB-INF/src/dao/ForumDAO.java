package dao;

import entity.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import util.HibernateUtil;
import util.Rewrapper;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForumDAO {
    private ForumDAO(){}
    public static final int EXPS_PER_LEVEL = 2000;

    public static boolean addTheme(String themename,String boardname){
        if(existTheme(themename,boardname))
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
        return CommonDAO.getItemByPK(ThemeEntity.class, pk) != null;
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
        if(entity==null)
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
                    "and entity.lastcomdatetime<= :time and entity.visibility = 'all' order by entity.lastcomdatetime desc";
            Query query = s.createQuery(hql);
            query.setParameter("bdnm",boardname);
            query.setParameter("tmnm",themename);
            query.setParameter("time",thisTimeBefore);
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
        return getPrivilige(userid) >= 4;
    }

    public static List getArticleCommentList(String articleid){
        String hql = "from ArticlecommentEntity entity where entity.articlecommentEntityPK.articleid =:aid";
        Map<String,Object> params = new HashMap<>();
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

    //默认按照文章发布时间排序
    //返回对象格式与文章列表相同,加上版块和栏目信息
    public static List getCollectionList(String userid,int startat,int number){
        Session s = null;
        List result = null;
        List wrappedResult = null;
        try {
            s = HibernateUtil.getSession();
            String getArticleIDList = "select entity.collectionEntityPK.articleid from CollectionEntity entity where entity.collectionEntityPK.userid = :uid order by entity.collectdatetime desc";
            Query query = s.createQuery(getArticleIDList);
            query.setParameter("uid",userid);
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
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }
        return wrappedResult;
    }

    //返回格式与文章列表相同，每个文章只有一条最近浏览的记录
    public static List getViewHistory(String userid,Timestamp lasttime,int startat,int number){
        Session s = null;
        List result = null;
        List wrappedResult = null;
        try {
            s = HibernateUtil.getSession();
            String getArticleIDList = "select distinct entity.viewrecordEntityPK.articleid " +
                    "from ViewrecordEntity entity where entity.viewrecordEntityPK.userid = :uid " +
                    "and entity.viewrecordEntityPK.viewdatetime<=:time " +
                    "order by entity.viewrecordEntityPK.viewdatetime desc";
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
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            HibernateUtil.safeCloseSession(s);
        }
        return wrappedResult;
    }

    public static boolean clearViewHistory(String userid){
        String hql = "delete Viewrecord record where record.viewrecordEntityPK.userid = :uid";
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

    //包装后的map包含：用户id、昵称、性别、个性签名、学校、专业，用户论坛等级和经验，用户权限，
    public static List getWrappedForumAccountInfo(String userid){

        return null;

    }
}
