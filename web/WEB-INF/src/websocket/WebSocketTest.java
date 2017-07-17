/**
package websocket;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



 * Created by Administrator on 2017/7/17.


@ServerEndpoint(value = "/websocket/{userid}",configurator = SpringConfigurator.class)
public class WebSocketTest {
    public static Map<String,WebSocketTest> sockets = new HashMap<>();
    private String userid;
    private Session session;

    @OnOpen
    public void onOpen(@PathParam("userid") String userid
                        ,Session s){
        this.session = s;
        this.userid = userid;
        sockets.put(userid,this);
        System.out.println("begin a link");
    }

    @OnMessage
    public void onMessage(String message,Session s){
        System.out.println(this.userid +" user send message: "+message);
        sendMessage(this.userid,"i get your message: "+message);
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