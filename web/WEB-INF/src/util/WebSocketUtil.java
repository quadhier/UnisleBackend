package util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import websocket.WebSocketHandler;

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
}
