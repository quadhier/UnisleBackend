package controller;

import com.sun.org.apache.regexp.internal.RE;
import com.sun.tools.corba.se.idl.constExpr.Times;
import converter.ResultInfo;
import dao.CommonDAO;
import dao.ForumDAO;
import entity.ArticleEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.jvm.hotspot.runtime.ResultTypeFinder;
import util.ControllerUtil;

import javax.management.relation.RelationSupport;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by qudaohan on 2017/7/20.
 */

/*
*
* Privilege:
* 0. outsider
* 1. reader
* 2. commenter
* 3. author
* 4. editor
* 5. boarder
* 6. supervisor
*
* */

@Controller
@RequestMapping("/forum")
public class ForumController {


    // Helper Function
    // 将整型权限的转化为字符串
    private String getStrPri(int intPri) {

        if(intPri < 0 ||  intPri > 6)
            return "outsider";
        String[] strPris = {"outsider", "reader", "commenter", "author", "editor", "boarder", "supervisor"};
        return strPris[intPri];
    }

    // Helper Function
    // 将字符串权限转化为整型
    private int getIntPri(String strPri) {

        int intPri = -1;
        String[] strPris = {"outsider", "reader", "commenter", "author", "editor", "boarder", "supervisor"};
        for(int i = 0; i < 7; i++) {
            if(strPris[i].equals(strPri)) {
                intPri = i;
                break;
            }
        }
        if(intPri == -1)
            intPri = 0;
        return intPri;
    }

    private int getUserPri(String userid) {
        return ForumDAO.getPrivilige(userid);
    }


    /*
    *
    * 权限有关操作
    *
    * */

    // 获取指定用户的权限
    @RequestMapping("/userpri")
    @ResponseBody
    public Object getPri(HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("SUCCESS");
        String userid = ControllerUtil.getUidFromReq(request);
        int intPri = getUserPri(userid);
        rinfo.setData(getStrPri(intPri));
        return rinfo;
    }


    // 更改用户的权限
    // 只有版主和超级管理员有此权限
    // 并且只能将权限修改比自己的权限低的位置
    @RequestMapping("/userpri")
    @ResponseBody
    public Object alterUserPri(@RequestParam("newPrivilege") String newPrivilege,
                               HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        // 权限检查
        String userid = ControllerUtil.getUidFromReq(request);
        if(getUserPri(userid) < getIntPri("boarder") ||
                getUserPri(userid) <= getIntPri(newPrivilege)) {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        if(ForumDAO.alterPrivilige(userid, getIntPri(newPrivilege)) == false) {
            rinfo.setReason("E_NOT_CHANGED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }



    /*
    *
    * 主题有关操作
    *
    * */

    // 添加主题
    // 只有超级管理员有此权限
    @RequestMapping(value = "/theme", method = RequestMethod.POST)
    @ResponseBody
    public Object addTheme(@RequestParam("themeName") String themeName,
                           @RequestParam("boardName") String boardName,
                           HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);
        if(getUserPri(userid) < getIntPri("supervisor")) {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        if(! ForumDAO.addTheme(themeName, boardName)) {
            rinfo.setReason("E_NOT_ADDED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;

    }


    // 删除主题
    // 只有超级管理员有此权限
    @RequestMapping(value = "/theme/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteTheme(@RequestParam("themeName") String themeName,
                              @RequestParam("boardName") String boardName,
                              HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);
        if(getUserPri(userid) < getIntPri("supervisor") ) {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        if( !ForumDAO.deleteTheme(themeName, boardName)) {
            rinfo.setReason("E_NOT_DELETED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;

    }


    // 主题更名
    // 只有超级管理员有此权限
    @RequestMapping(value = "/theme/changename", method = RequestMethod.POST)
    @ResponseBody
    public Object changeTheme(@RequestParam("oldThemeName") String oldThemeName,
                              @RequestParam("newThemeName") String newThemeName,
                              @RequestParam("boardName") String boardName,
                              HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);
        if(getUserPri(userid) < getIntPri("supervisor")) {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        if( !ForumDAO.alterThemeName(oldThemeName, newThemeName, boardName)) {
            rinfo.setReason("E_NOT_CHANGED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }



    /*
    *
    * 文章有关操作
    *
    * */

    // 获取用户的个人信息
    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserInfo(@RequestParam("userid") String userid,
                              HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("SUCCESS");

        if(userid == null)
            userid = ControllerUtil.getUidFromReq(request);
        rinfo.setData(ForumDAO.getWrappedForumAccountInfo(userid));
        return rinfo;
    }


    // 设置文章可见性
    // 只有版主或超级管理员有此权限
    // newVisibility can be "all" or "self"
    @RequestMapping(value = "/article/visibility", method = RequestMethod.GET)
    @ResponseBody
    public Object setVisibility(@RequestParam("articleid") String articleid,
                                @RequestParam("visibility") String newVisibility,
                                HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        if(newVisibility != null && !newVisibility.equals("all") && !newVisibility.equals("self")) {
            rinfo.setReason("E_INVALID_VISIBILITY");
            return rinfo;
        }

        String userid = ControllerUtil.getUidFromReq(request);
        if(getUserPri(userid) < getIntPri("boarder")) {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        if( !ForumDAO.setVisibilityAndCommentallowed(articleid, newVisibility,null) ) {
            rinfo.setReason("E_NOT_CHANGED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }


    // 获取特定板块的特定主题下的文章列表
    // 可以获取指定时间之前的，指定数量的文章（现改为当前系统时间）
    // 其中包含articleid，authorid，commentallowed，title，viewtimes，发布、最后修改、最后评论时间
    // 返回列表按照最后评论时间排序
    @RequestMapping(value = "/article/all", method = RequestMethod.GET)
    @ResponseBody
    public Object getArticleList(@RequestParam("boardName") String boardName,
                                 @RequestParam("themeName") String themeName,
                                 @RequestParam("beforeTime") String beforeTime,
                                 @RequestParam("startat") int startAt,
                                 @RequestParam("number") int number,
                                 HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        if(startAt < 0 || number < 0) {
            rinfo.setReason("E_INVALID_NUMBER");
            return rinfo;
        }

        String userid = ControllerUtil.getUidFromReq(request);

        Timestamp bTime = new Timestamp(System.currentTimeMillis());
        List articles = ForumDAO.getArticleList(userid, boardName, themeName, bTime, startAt, number);
        rinfo.setResult("SUCCESS");
        rinfo.setData(articles);
        return rinfo;
    }


    // 获取文章内容
    @RequestMapping(value = "/article/content", method = RequestMethod.GET)
    @ResponseBody
    public Object getArticleContent(@RequestParam("articleid") String articleid,
                                    HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);
        ArticleEntity article = (ArticleEntity) CommonDAO.getItemByPK(ArticleEntity.class, userid);
        if(!article.getAuthor().equals(userid) && article.getVisibility().equals("self")) {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        rinfo.setData(article.getContent());
        return rinfo;
    }


    // 保存正在编辑的文章
    // 也可以用来保存动态
    @RequestMapping(value = "/article/editing", method = RequestMethod.POST)
    @ResponseBody
    public Object saveEditingArticle(@RequestParam("type") String type,
                                     @RequestParam("content") String content,
                                     HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        if(!type.equals("article") && !type.equals("activity")) {
            rinfo.setResult("E_UNKNOWN_TYPE");
            return rinfo;
        }

        String userid = ControllerUtil.getUidFromReq(request);
        if( !ForumDAO.saveEditingWords(userid, type, content)) {
            rinfo.setReason("E_NOT_SAVED");
            return rinfo;
        }
        rinfo.setResult("SUCCESS");
        return rinfo;
    }


    // 获取正在编辑的文章
    // 也可以用来获取正在编辑的动态
    @RequestMapping(value = "/article/editing", method = RequestMethod.GET)
    @ResponseBody
    public Object getEditingArticle(@RequestParam("type") String type,
                                    HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        if(!type.equals("article") && !type.equals("activity")) {
            rinfo.setReason("E_UNKNOWN_TYPE");
            return rinfo;
        }

        String userid = ControllerUtil.getUidFromReq(request);
        String content =  ForumDAO.getEditingWords(userid, type);
        rinfo.setResult("SUCCESS");
        rinfo.setData(content);
        return rinfo;
    }

    // 发布文章
    @RequestMapping(value = "/article/publish", method = RequestMethod.POST)
    @ResponseBody
    public Object publishAriticle(@RequestParam("boardName") String boardName,
                                  @RequestParam("themeName") String themeName,
                                  @RequestParam("title") String title,
                                  @RequestParam("content") String content,
                                  @RequestParam("commentAllowed") String commentAllowed,
                                  HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("SUCCESS");

        String userid = ControllerUtil.getUidFromReq(request);
        String articleid = ForumDAO.publishArticle(userid, themeName, boardName, title, content, commentAllowed);
        rinfo.setData(articleid);
        return rinfo;
    }

    // 更改文章内容
    // 只有文章发布者可以更改文章内容
    @RequestMapping(value = "/article／alter", method = RequestMethod.POST)
    @ResponseBody
    public Object alterArticleContent(@RequestParam("articleid") String articleid,
                                      @RequestParam("newContent") String newContent,
                                      HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        // 权限检查
        String userid = ControllerUtil.getUidFromReq(request);
        ArticleEntity article = (ArticleEntity) CommonDAO.getItemByPK(ArticleEntity.class, articleid);
        if(!article.getAuthor().equals(userid)) {
            rinfo.setReason("E_NOT_ALLOWED");
            return rinfo;
        }

        if(!ForumDAO.alterArticleContent(articleid, newContent)) {
            rinfo.setReason("E_NOT_ALTERED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }


    // 获取我的文章列表
    // 指定获取从某篇文章开始以后的一定数目的文章
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    @ResponseBody
    public Object getMyArticle(@RequestParam("userid") String article,
                               @RequestParam("startAt") int startAt,
                               @RequestParam("number") int number,
                               HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        if(startAt < 0 || number < 0) {
            rinfo.setReason("E_INVALID_NUMBER");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        String userid = ControllerUtil.getUidFromReq(request);
        rinfo.setData(ForumDAO.getMyArticleList(userid, startAt, number));
        return rinfo;
    }


    // 删除文章
    @RequestMapping(value = "/article/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteArticle(@RequestParam("articleid") String articleid,
                                HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);
        ArticleEntity article = (ArticleEntity) CommonDAO.getItemByPK(ArticleEntity.class, articleid);
        if(!article.getAuthor().equals(userid) && getUserPri(userid) < getIntPri("boarder")) {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        if(!ForumDAO.deleteArticle(articleid)) {
            rinfo.setReason("E_NOT_DELETED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }

    // 设置是否能够评论
    // 只有小编，版主和超级管理员有此权限
    @RequestMapping(value = "/article/commentpermission", method = RequestMethod.POST)
    @ResponseBody
    public Object setCommentPermission(@RequestParam("articleid") String articleid,
                                       @RequestParam("commentpermisson") String commentpermission,
                                       HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        if(commentpermission != null && !commentpermission.equals("yes") && !commentpermission.equals("no")) {
            rinfo.setReason("E_INVALID_COMMENTPERMISSION");
            return rinfo;
        }

        String userid = ControllerUtil.getUidFromReq(request);

        if(! (getUserPri(userid) < getIntPri("editor")) ) {
            rinfo.setReason("E_INSUFFICIENT_PERMISSION");
            return rinfo;
        }

        if(!ForumDAO.setVisibilityAndCommentallowed(articleid, null, commentpermission)) {
            rinfo.setReason("E_NOT_SET");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }


    /*
    *
    * 评论有关操作
    *
    * */

    // 发布评论
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ResponseBody
    public Object publishArticleComment(@RequestParam("articleid") String articleid,
                                        @RequestParam("content") String content,
                                        HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        if(!ForumDAO.isCommentAllowed(articleid)) {
            rinfo.setReason("E_COMMENT_NOT_ALLOWED");
            return rinfo;
        }

        String userid = ControllerUtil.getUidFromReq(request);
        if(!ForumDAO.publishArticleComment(userid, articleid, content)) {
            rinfo.setReason("E_NOT_PUBLISHED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }


    // 获取文章的评论列表
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    @ResponseBody
    public Object getArticleComment(@RequestParam("articleid") String articleid,
                                    HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("SUCCESS");
        List articleComment = ForumDAO.getArticleCommentList(articleid);
        rinfo.setData(articleComment);
        return rinfo;
    }


    /*
    *
    * 收藏夹有关操作
    *
    * */

    // 查看用户的收藏夹
    @RequestMapping(value = "/collection", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserCollection(@RequestParam("userid") String userid,
                                    @RequestParam("startAt") int startAt,
                                    @RequestParam("endAt") int number) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        if(startAt < 0 || number < 0) {
            rinfo.setReason("E_INVALID_NUMBER");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        List collection = ForumDAO.getCollectionList(userid, startAt, number);
        rinfo.setData(collection);
        return rinfo;
    }



    // 将文章加入收藏夹
    @RequestMapping(value = "/collection", method = RequestMethod.POST)
    @ResponseBody
    public Object addToCollection(@RequestParam("userid") String userid,
                                  @RequestParam("articleid") String articleid,
                                  HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("SUCCESS");

        if(!ForumDAO.addToCollection(userid, articleid)) {
            rinfo.setReason("E_NOT_ADDED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }


    // 将文章从收藏夹中删除
    @RequestMapping(value = "/collection/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteFromCollection(@RequestParam("articleid") String articleid,
                                       HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);
        if(!ForumDAO.deleteFromCollection(userid, articleid)) {
            rinfo.setReason("E_NOT_DELETED");
            return rinfo;
        }

        rinfo.setReason("SUCCESS");
        return rinfo;
    }


    /*
    *
    * 历史记录有关操作
    *
    * */

    // 获取用户的浏览历史记录
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    @ResponseBody
    public Object getHistory(@RequestParam("userid") String userid,
                             @RequestParam("lastTime") String lastTime,
                             @RequestParam("startAt") int startAt,
                             @RequestParam("number") int number) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        if(startAt < 0 || number < 0) {
            rinfo.setReason("E_INVALID_NUMBER");
            return rinfo;
        }

        Timestamp sTime = null;
        try {
            sTime = new Timestamp(Long.valueOf(lastTime));
        } catch (Exception e) {
            e.printStackTrace();
            rinfo.setReason("E_MALFORMED_TIME");
            return rinfo;
        }

        List history = ForumDAO.getViewHistory(userid, sTime, startAt, number);
        rinfo.setResult("SUCCESS");
        rinfo.setData(history);
        return rinfo;
    }


    // 删除单条历史记录
    @RequestMapping(value = "/history/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteHistory(@RequestParam("articleid") String articleid,
                                HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);
        if(!ForumDAO.deleteViewHistory(userid, articleid)) {
            rinfo.setReason("E_NOT_DELETED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }


    // 删除所有历史记录
    @RequestMapping(value = "/history/deleteall", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteAllHistory(HttpServletRequest request) {

        ResultInfo rinfo = new ResultInfo();
        rinfo.setResult("ERROR");

        String userid = ControllerUtil.getUidFromReq(request);
        if(!ForumDAO.clearViewHistory(userid)) {
            rinfo.setReason("E_NOT_DELETED");
            return rinfo;
        }

        rinfo.setResult("SUCCESS");
        return rinfo;
    }



}
