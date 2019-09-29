package Network;

import java.io.Serializable;
import java.util.UUID;

public class UserName implements Serializable {
    public String userName;
    public UUID uuid;

    public UserName(String userName) {
        this.userName = userName;
        uuid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserName)) return false;

        UserName userName1 = (UserName) o;

        if (!userName.equals(userName1.userName)) return false;
        return uuid.equals(userName1.uuid);
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + uuid.hashCode();
        return result;
    }
}
