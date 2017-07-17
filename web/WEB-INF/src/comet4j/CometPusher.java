package comet4j;

import org.comet4j.core.CometContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;

/**
 * Created by Administrator on 2017/7/17.
 */
public class CometPusher implements ServletContextListener{
    private static final String channel1 = "CHANNEL_1";
    private static final String channel2 = "CHANNEL_2";

    private static String pushContent1 = "this is 1 ";
    private static String pushContent2 = "this is 2";

    class SendToServerThread implements Runnable{
        public void run(){
            while(true){
                try {
                    Thread.sleep(1000);
                }catch(Exception e){
                    e.printStackTrace();
                }

                CometEngine engine = CometContext.getInstance().getEngine();
                engine.sendToAll(channel1,pushContent1 + System.currentTimeMillis());
                engine.sendToAll(channel2,pushContent2);
            }
        }
    }

    public void contextInitialized(ServletContextEvent event){
        CometContext context = CometContext.getInstance();
        context.registChannel(channel1);
        context.registChannel(channel2);

        Thread pusher = new Thread(new SendToServerThread(),"SendToClientThread");
        pusher.setDaemon(true);
        pusher.start();
    }



    public void contextDestroyed(ServletContextEvent e){

    }
}
