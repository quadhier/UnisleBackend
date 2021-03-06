package websocket;

import dao.ChatDAO;
import dao.NoticeDAO;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import util.WebSocketUtil;

import java.sql.Timestamp;
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
            System.out.println(content);
            String askcode = WebSocketUtil.getStringByKey("askcode", content);
            if (askcode.equals("000")) {
                String userid = WebSocketUtil.getStringByKey("userid", content);
                if(channels.containsKey(userid)){
                    List<WebSocketSession> sessionList = channels.get(userid);
                    if(!sessionList.contains(session))
                        sessionList.add(session);
                    System.out.println(userid+"的在线网页数:"+sessionList.size());
                }else{
                    List<WebSocketSession> sessionList = new LinkedList<>();
                    sessionList.add(session);
                    channels.put(userid,sessionList);
                    System.out.println(userid+"的在线网页数: #1");
                }
                System.out.println("在线用户数："+channels.size());
            } else if (askcode.equals("999")) {
                String userid = WebSocketUtil.getStringByKey("userid", content);
                if(channels.containsKey(userid)){
                    List<WebSocketSession> sessionList = channels.get(userid);
                    if(sessionList.contains(session))
                        sessionList.remove(session);
                    if(sessionList.isEmpty())
                        channels.remove(userid);

                    if(sessionList!=null)
                        System.out.println(userid+"的在线网页数:"+sessionList.size());
                    else
                        System.out.println(userid+"的在线网页数: *0");
                }
                System.out.println("在线用户数："+channels.size());
            }else if(askcode.equals("100")){
                String senderid = WebSocketUtil.getStringByKey("senderid",content);
                String receiverid = WebSocketUtil.getStringByKey("receiverid",content);
                String messageContent = WebSocketUtil.getStringByKey("content",content);
                String current = WebSocketUtil.getStringByKey("senddatetime",content);
                Timestamp now = new Timestamp(Long.parseLong(current));
                ChatDAO.sendMessage(senderid,receiverid,messageContent,now);
                WebSocketUtil.sendMessageIfOnline(senderid,receiverid,now,messageContent);
            }
        }catch (Exception e) {
            e.printStackTrace();
            for (Map.Entry<String, List<WebSocketSession>> entry : channels.entrySet()) {
                if (entry.getValue().contains(session)) {
                    String user = entry.getKey();
                    List<WebSocketSession> sessionList = channels.get(user);
                    if(sessionList.contains(session))
                        sessionList.remove(session);
                    if(sessionList.isEmpty())
                        channels.remove(user);
                    break;
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //session.sendMessage(new TextMessage("link start"));
        System.out.println("start link");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        for (Map.Entry<String, List<WebSocketSession>> entry : channels.entrySet()) {
            if (entry.getValue().contains(session)) {
                String user = entry.getKey();
                List<WebSocketSession> sessionList = channels.get(user);
                if(sessionList.contains(session))
                    sessionList.remove(session);
                if(sessionList.isEmpty())
                    channels.remove(user);
                break;
            }
        }
    }
}