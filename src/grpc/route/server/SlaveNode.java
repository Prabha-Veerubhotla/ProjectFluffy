package grpc.route.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SlaveNode extends RouteServerImpl {

    static Map<String, List<String>> map = new HashMap<>();

    public static boolean saveMessage(String msg, String name) {
        logger.info("saving message:  "+msg+" from:  "+name+" in slave..");
        if(map.containsKey(name)) {
            logger.info("saving next message of "+name);
            List<String> messages = map.get(name);
            messages.add(msg);
            map.put(name, messages);

        } else {
            logger.info("saving first message of " + name);
            List<String> messages = new ArrayList<>();
            messages.add(msg);
            map.put(name, messages);

        }
        return true;
    }

    public static String getSavedMessage(String msg, String name) {
        logger.info("retrieving message from slave..");
        String result = null;
        if(map.containsKey(name)) {
            logger.info(name +" has saved messages");
            List<String> messagesList = map.get(name);
            if(messagesList.contains(msg)) {
               logger.info("retrieving "+msg+ " from saved messages of "+name);
                result = messagesList.get(messagesList.indexOf(msg));
            }
            else {
                logger.info("message is either deleted or not saved");
            }
        } else {
            logger.info(name+ " does not have any saved messages");
        }
        return result;
    }


    public static boolean deleteMessage(String msg, String name) {
        boolean status = false;
        logger.info("deleting message "+msg+" from:  "+name+" in slave..");
        if(map.containsKey(name)) {
            List<String> messages = map.get(name);
            messages.remove(msg);
            map.put(name, messages);
            status = true;
        }
        return status;
    }

    public static List<String> listMessages(String name) {
        List<String> stringList = new ArrayList<>();
        if(map.containsKey(name)) {
            return map.get(name);
        }
        return stringList;
    }


    // 1. put data into the database
    public static boolean putFilePartition(String filename) {

        return true;
    }


    // 2. reqtrieve data from the database and send it to server upon a request

    public static byte[] getFilePartition(String filename) {

        return new byte[5];
    }

    // 3. talk with master node
    public void sendMessageToMaster() {

    }

    // v2: 4. maintain a in memory, cache
}