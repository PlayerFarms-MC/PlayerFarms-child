package ml.pkom.playerfarms.childserver.api;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ml.pkom.jsonconfig.api.JsonConfiguration;
import ml.pkom.playerfarms.childserver.PlayerFarmsChild;

public class Language {
    private File pluginFolder = PlayerFarmsChild.getInstance().getDataFolder();

    private JsonConfiguration jConfig = new JsonConfiguration(new File(pluginFolder, "lang/ja_jp.json"));
    private FileConfiguration config;
    private static Language lang;

    public static Object getLang(String str){
        return lang.config.get(str);
    }

    public static String getLangStr(String str) {
        String res = lang.config.getString(str);
        if (res == null) {
            return "null";
        }
        return res;
    }

    public static int getLangInt(String str) {
        return lang.config.getInt(str);
    }

    public Language(){
        lang = this;
        if (!pluginFolder.exists())
            pluginFolder.mkdir();
        File langFolder = new File(pluginFolder, "lang/");
        if (!langFolder.exists())
            langFolder.mkdir();

        config = new YamlConfiguration();
        if (new File(pluginFolder, "lang/ja_jp.json").exists()) {
            config = jConfig.getJsonConfig();
        } else {
            config.set("gui.realmsettings.title", "Realm Settings");
            config.set("gui.realmsettings.plugin_manager_page.title", "§dPlugin Manager Page ");
            config.set("gui.realmsettings.edit_gamerule.title", "Editting '$world$' Gamerules");
            config.set("gui.realmsettings.config.title", "plugins");
            config.set("gui.realmsettings.config_shop.title", "Configure Server Shop");
            config.set("gui.realmsettings.realmspex.title", "Permission Setting Page ");
            config.set("gui.realmsettings.iconedit.title", "§9Select a Icon Page ");
            config.set("gui.realmsettings.edit_skript.title", "Edit Skripts Page ");
            config.set("gui.realmsettings.viewserver.name", "§aサーバーが見えます。");
            config.set("gui.realmsettings.viewserver.lore", "§7このサーバーがサーバーリストのリストで\n見えるかどうかを変更できます。\n友達は「/server $servername$」で入れます。");
        }
        jConfig.setJsonConfig(config);
        jConfig.saveJsonConfig();
    }
}
