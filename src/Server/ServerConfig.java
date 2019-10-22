package Server;

import java.io.FileInputStream;
import java.util.Properties;

public class ServerConfig {
    public static final String CONFIG_FILE = "server.properties";

    public static int SERVER_PORT;

    public static void loadConfig() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream(ServerConfig.CONFIG_FILE));

        SERVER_PORT = Integer.parseInt(properties.getProperty("server_port"));
    }
}
