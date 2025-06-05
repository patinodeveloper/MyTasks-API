package my_tasks.helpers;

import my_tasks.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserHelper {
    public boolean isAdmin(User user) {
        return user.getRole().getAuthority().equals("ROLE_ADMIN");
    }
}
