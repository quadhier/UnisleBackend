
package websocket;
/*
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



@ServerEndpoint(value = "/websocket/test",configurator = SpringConfigurator.class)
public class WebSocketTest {
    public static Map<String,WebSocketTest> sockets = new HashMap<>();
    private String userid;
    private Session session;

    @OnOpen
    public void onOpen(//@PathParam("userid") String userid
                        Session s){
        this.session = s;
        this.userid = userid;
        sockets.put(userid,this);
        System.out.println("begin a link");
    }

    @OnMessage
    public void onMessage (String message,Session s) throws Exception {
       // System.out.println(this.userid +" user send message: "+message);
       // sendMessage(this.userid,"i get your message: "+message);
        s.getBasicRemote().sendText("i get your message:"+message);
    }

    @OnClose
    public void onClose(){
        sockets.remove(this.userid);
    }

    @OnError
    public void onError(Session session,Throwable error){
        System.out.println("websocket error!");
    }

    public boolean sendMessage (String userid,String msg){
        try {
            System.out.println("receive message socket size: "+sockets.size());
            Session userSession = sockets.get(userid).session;
            userSession.getBasicRemote().sendText(msg);
        }catch(Exception e){
            e.printStackTrace();;
            return false;
        }

        return true;
    }
}
*/
/**
 * @Class: WebsocketDemo
 * @Description:  给所用户所有终端推送消息
 * @author JFPZ
 * @date 2017年5月15日 上午21:38:08

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.web.socket.server.standard.SpringConfigurator;

//websocket连接URL地址和可被调用配置
@ServerEndpoint(value="/websocketDemo/{userId}",configurator = SpringConfigurator.class)
public class WebSocketTest {
    //日志记录
    //private Logger logger = LoggerFactory.getLogger(WebsocketDemo.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //记录每个用户下多个终端的连接
    private static Map<Long, Set<WebSocketTest>> userSocket = new HashMap<>();

    //需要session来对用户发送数据, 获取连接特征userId
    private Session session;
    private Long userId;

    /**
     * @Title: onOpen
     * @Description: websocekt连接建立时的操作
     * @param @param userId 用户id

    @OnOpen
    public void onOpen(@PathParam("userId") Long userId,Session session) throws IOException{
        this.session = session;
        this.userId = userId;
        onlineCount++;
        //根据该用户当前是否已经在别的终端登录进行添加操作
        if (userSocket.containsKey(this.userId)) {
            System.out.println("当前用户id已有其他终端登录");
            userSocket.get(this.userId).add(this); //增加该用户set中的连接实例
        }else {
            System.out.println("当前用户id第一个终端登录");
            Set<WebSocketTest> addUserSet = new HashSet<>();
            addUserSet.add(this);
            userSocket.put(this.userId, addUserSet);
        }
        System.out.println("用户登录的终端个数是为"+userSocket.get(this.userId).size());
        System.out.println("当前在线用户数为"+userSocket.size());
    }

    /**
     * @Title: onClose
     * @Description: 连接关闭的操作

    @OnClose
    public void onClose(){
        //移除当前用户终端登录的websocket信息,如果该用户的所有终端都下线了，则删除该用户的记录
        if (userSocket.get(this.userId).size() == 0) {
            userSocket.remove(this.userId);
        }else{
            userSocket.get(this.userId).remove(this);
        }
       // logger.debug("用户{}登录的终端个数是为{}",this.userId,userSocket.get(this.userId).size());
       // logger.debug("当前在线用户数为：{},所有终端个数为：{}",userSocket.size(),onlineCount);
    }


    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("收到来自用户id为的消息"+this.userId+message);
        if(session ==null)  System.out.println("session null");
    }

    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("用户id为的连接发送错误"+this.userId);
        error.printStackTrace();
    }


    public Boolean sendMessageToUser(Long userId,String message){
        if (userSocket.containsKey(userId)) {
            System.out.println(" 给用户id为：{}的所有终端发送消息：{}"+userId+message);
            for (WebSocketTest WS : userSocket.get(userId)) {
                System.out.println("sessionId为:{}"+WS.session.getId());
                try {
                    WS.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(" 给用户id为：{}发送消息失败"+userId);
                    return false;
                }
            }
            return true;
        }
        System.out.println("发送错误：当前连接不包含id为：{}的用户"+userId);
        return false;
    }

}
        */