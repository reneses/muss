package ie.cit.adf.muss.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;

import ie.cit.adf.muss.domain.User;

@Component
public class APIService {

    @Value("${muss.key}")
    String key;

    private static final ShaPasswordEncoder encoder = new ShaPasswordEncoder();

    // Limit the requests to those done in less that one day
    private static final long MAX_REQUEST_DELAY = 1000 * 60 * 60 * 24;

    private String getHMAC(int userID, long time) {
        return encoder.encodePassword(key + userID, time);
    }

    public String getHMAC(User user, Date date) {
        if (user == null || date == null)
            throw new IllegalArgumentException();
        return getHMAC(user.getId(), date.getTime());
    }

    public void validateRequest(String expected, int userID, long time) throws IllegalAccessException {

        // Validate fields
        if (expected == null || expected.equals("") || userID <= 0 || time <= 0)
            throw new IllegalAccessException();

        // Check the HMAC is the same
        if (!encoder.isPasswordValid(expected, key + userID, time))
            throw new IllegalAccessException();

        // Check that the time difference is allowed
        if (Math.abs(new Date().getTime() - time) > MAX_REQUEST_DELAY)
            throw new IllegalAccessException();

    }

}
