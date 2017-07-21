package util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import websocket.WebSocketHandler;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/19.
 */
public class WebSocketUtil {
    public static String getStringByKey(String key,String json){
        JsonObject forRead = new JsonParser().parse(json).getAsJsonObject();
        return forRead.get(key).getAsString();
    }

    public static boolean sendToAllPages(String userid,String text){
        if(!WebSocketHandler.channels.containsKey(userid))
            return false;

        try {
            List<WebSocketSession> allChannels = WebSocketHandler.channels.get(userid);
            TextMessage msg = new TextMessage(text);
            for(WebSocketSession s:allChannels)
                s.sendMessage(msg);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean isOnline(String userid){
        return WebSocketHandler.channels.containsKey(userid);
    }

    public static boolean sendNoticeIfOnline(String sender, String receiver, String type, Timestamp sendtime,String content){
        if(!isOnline(receiver))
            return false;
        String json ="{"+
                "\"returncode\":\"200\" ,"+
                "\"senderid\":\""+ sender +"\" ," +
                "\"noticetype\":\""+ type +"\" ," +
                "\"sendtime\":\""+ sendtime.toString() +"\" ," +
                "\"content\":\""+ content +"\" }";
        return sendToAllPages(receiver,json);
    }

    public static boolean sendMessageIfOnline(String sender, String receiver, Timestamp sendtime,String content){
        if(!isOnline(receiver))
            return false;
        String json ="{"+
                "\"returncode\":\"102\" ,"+
                "\"senderid\":\""+ sender +"\" ," +
                "\"sendtime\":\""+ sendtime.toString() +"\" ," +
                "\"content\":\""+ content +"\" }";
        return sendToAllPages(receiver,json);
    }
}
