package ml.pkom.playerfarms.childserver.api;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ml.pkom.jsonconfig.api.JsonConfiguration;
import ml.pkom.playerfarms.childserver.PlayerFarmsChild;

public class States {
    private File pluginFolder = PlayerFarmsChild.getInstance().getDataFolder();

    private FileConfiguration config = new YamlConfiguration();;
    private JsonConfiguration jConfig = new JsonConfiguration(new File(pluginFolder, "states.json"));
    
    private static States states;

    public static States getInstance(){
        return states;
    }

    public States(){
        states = this;
        if (!pluginFolder.exists())
            pluginFolder.mkdir();
    }

    public void setConfig(String name, String value) {
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
