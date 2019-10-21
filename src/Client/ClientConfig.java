package Client;

import java.io.FileInputStream;
import java.util.Properties;

public class ClientConfig {
    public static final String CONFIG_FILE = "client.properties";

    public static final int DEFAULT_SIZE = 10;
    public static final int SLIDER_MAX = 50;
    public static final int SLIDER_MIN = 1;

    public static final String CHAT_ROOM_STRING = "Chat Room";

    public static final String CUSTOM_EXTENSION_DESCRIPTION = "Whiteboard Data (*.wbd)";
    public static final String CUSTOM_EXTENSION = "wbd";

    public static final String ERROR_BOX_TITLE = "ERROR";
    public static final String CLIENT_CONFIG_ERROR_MSG =
            "Error occurred during reading configuration from " + CONFIG_FILE;

    public static int WIDTH;
    public static int HEIGHT;

    public static String SERVER_ADDRESS;
    public static int SERVER_PORT;
    public static String DEFAULT_ROOM_NAME;

    public static String DEFAULT_USERNAME;

    public static void loadConfig() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream(ClientConfig.CONFIG_FILE));

        WIDTH = Integer.parseInt(properties.getProperty("width"));
        HEIGHT = Integer.parseInt(properties.getProperty("height"));

        SERVER_ADDRESS = properties.getProperty("server_address");
        SERVER_PORT = Integer.parseInt(properties.getProperty("server_port"));
        DEFAULT_ROOM_NAME = properties.getProperty("room_name");

        DEFAULT_USERNAME = properties.getProperty("username");
    }
}
