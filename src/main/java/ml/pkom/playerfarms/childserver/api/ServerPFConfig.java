package ml.pkom.playerfarms.childserver.api;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ml.pkom.jsonconfig.api.JsonConfiguration;
import ml.pkom.playerfarms.childserver.PlayerFarmsChild;

public class ServerPFConfig {
    private File pluginFolder = PlayerFarmsChild.getInstance().getDataFolder();

    private FileConfiguration config = new YamlConfiguration();;
    private JsonConfiguration jConfig = new JsonConfiguration(new File(pluginFolder.getAbsoluteFile().getParentFile().getParentFile().getParentFile().getParentFile(), "players/" + PlayerFarmsChild.ownerUUID + ".json"));
    
    private static ServerPFConfig instance;

    public static ServerPFConfig getInstance(){
        return instance;
    }

    public ServerPFConfig(){
        instance = this;
        config = jConfig.getJsonConfig();
    }

    public void setConfig(String name, String value) {
        if (!pluginFolder.exists())
            pluginFolder.mkdir();
        config.set(name, value);
        jConfig.setJsonConfig(config);
        jConfig.saveJsonConfig();
    }

    public void setIntConfig(String name, int value) {
        if (!pluginFolder.exists())
            pluginFolder.mkdir();
        config.set(name, value);
        jConfig.setJsonConfig(config);
        jConfig.saveJsonConfig();
    }

    public String getConfig(String name) {
        config = jConfig.getJsonConfig();
        return config.getString(name);
    }

}
