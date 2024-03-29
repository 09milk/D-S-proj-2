package Network;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    public final String nameWithId;
    public String userName;
    public UUID uuid;
    public short displayId;
    public boolean isManager = false;

    public User(String userName) {
        this.userName = userName;
        uuid = UUID.randomUUID();
        displayId = getDisplayId();
        nameWithId = String.format("%s(%d)", userName, displayId);
    }

    public User(User user) {
        this.userName = user.userName;
        uuid = user.uuid;
        displayId = user.displayId;
        isManager = user.isManager;
        nameWithId = user.nameWithId;
    }

    private short getDisplayId() {
        long xorLong = uuid.getLeastSignificantBits() ^ uuid.getMostSignificantBits();
        int xorInt = (int) (xorLong ^ (xorLong >>> 32));
        return (short) ((xorInt ^ (xorInt >>> 16)) & 0x7fff);
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
