package Network;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    public String userName;
    public UUID uuid;

    public User(String userName) {
        this.userName = userName;
        uuid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!userName.equals(user.userName)) return false;
        return uuid.equals(user.uuid);
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + uuid.hashCode();
        return result;
    }
}
