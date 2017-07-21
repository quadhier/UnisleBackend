package websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import util.WebSocketUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class WebSocketHandler extends TextWebSocketHandler {
    public static Map<String,List<WebSocketSession>> channels = new HashMap<>();

    @Override
    public void handleTextMessage (WebSocketSession session, TextMessage text){
        try {
            String content = text.getPayload();
            String askcode = WebSocketUtil.getStringByKey("askcode", content);
            String userid = WebSocketUtil.getStringByKey("userid", content);
            if (askcode.equals("000")) {
                if(channels.containsKey(userid)){
                    List<WebSocketSession> sessionList = channels.get(userid);
                    if(!sessionList.contains(session))
                        sessionList.add(session);
                }else{
                    List<WebSocketSession> sessionList = new LinkedList<>();
                    sessionList.add(session);
                    channels.put(userid,sessionList);
                }
            } else if (askcode.equals("999")) {
                List<WebSocketSession> sessionList = channels.get(userid);
                if(channels.containsKey(userid) && sessionList.contains(session)){
                    sessionList.remove(session);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            for (Map.Entry<String, List<WebSocketSession>> entry : channels.entrySet()) {
                if (entry.getValue().contains(session)) {
                    entry.getValue().remove(session);
                    break;
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("link start"));
        System.out.println("start link");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        for (Map.Entry<String, List<WebSocketSession>> entry : channels.entrySet()) {
            if (entry.getValue().contains(session)) {
                entry.getValue().remove(session);
                break;
            }
        }
    }
}

