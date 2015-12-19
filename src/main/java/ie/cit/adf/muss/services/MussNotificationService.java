package ie.cit.adf.muss.services;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.domain.notifications.MussNotification;

@Service
public class MussNotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static Set<String> declaredQueues = new HashSet<>();

    private void declareQueue(String key) {
        try {
            if (!declaredQueues.contains(key)) {
                Channel channel = rabbitTemplate.getConnectionFactory().createConnection().createChannel(false);
                channel.queueDeclare(key, true, false, false, null);
                declaredQueues.add(key);
            }
        } catch (IOException ignored) {

        }
    }

    private String encode(MussNotification notification) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(notification);
            return Base64.getEncoder().encodeToString(out.toByteArray());
        }
    }

    private MussNotification decode(String encoded) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(encoded);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return (MussNotification) ois.readObject();
        }
    }

    public void notificateFollowers(MussNotification notification, User actor) {
//        System.out.println("NEW NOTIFICATION: " + notification);
//        actor.getFollowers().forEach(follower ->
//                notificate(notification, String.valueOf(follower.getId()))
//        );
    }

    private void notificate(MussNotification notification, String key) {
        try {
            declareQueue(key);
            rabbitTemplate.convertAndSend(key, encode(notification));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MussNotification> getNotifications(User user) {
		return new ArrayList<MussNotification>();
//        List<MussNotification> notifications = new ArrayList<>();
//        String key = String.valueOf(user.getId());
//        while (true) {
//            try {
//                declareQueue(key);
//                String message = (String) rabbitTemplate.receiveAndConvert(key);
//                if (message == null)
//                    break;
//                notifications.add(0, decode(message));
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (ShutdownSignalException e) {
//                return notifications;
//            }
//        }
//        // TODO improve this way of restoring the notifications
//        for (int i = notifications.size() - 1; i >= 0; i--)
//            notificate(notifications.get(i), key);
//        return notifications;
    }


}
