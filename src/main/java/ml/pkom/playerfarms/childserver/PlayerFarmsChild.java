package ml.pkom.playerfarms.childserver;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ml.pkom.playerfarms.childserver.api.PlayerFarmsAPI;
import ml.pkom.playerfarms.childserver.api.ServerPFConfig;
import ml.pkom.playerfarms.childserver.api.States;

public class PlayerFarmsChild extends JavaPlugin {

    public final String MOD_ID = "playerfarms-child"; // MODID
    public final String MOD_NAME = "PlayerFarms"; // MOD名
    public final String MOD_VER = "1.0.0"; // MODバージョン
    public final String MOD_AUT = "Pitan"; // MOD開発者

    public static String ownerUUID = "";

    private static PlayerFarmsChild playerFarmsChild;
    private States fixedStates;
    private static File onlineServersFile;

    public static PlayerFarmsChild getInstance() {
        return playerFarmsChild;
    }

    @Override
    public void onEnable() {
        playerFarmsChild = this;
        new States();
        fixedStates = States.getInstance();
        fixedStates.setConfig("server.state", "online");
        onlineServersFile = new File(getDataFolder(), "../../../../OnlineServers.txt");
        ownerUUID = getDataFolder().getAbsoluteFile().getParentFile().getParentFile().getName();
        String onlineServersData = "";
        if (onlineServersFile.exists()) {
            onlineServersData = PlayerFarmsAPI.fileReadContents(onlineServersFile);
        }
        if (onlineServersData == null) {
            onlineServersData = "";
        }
        if (!onlineServersData.contains(ownerUUID)) {
            PlayerFarmsAPI.fileWriteContents(onlineServersFile, ownerUUID + "\n" + onlineServersData);
        }
        new ServerPFConfig();
        Bukkit.getPluginManager().registerEvents(new JoinQuit(), this);
        getLogger().info(MOD_NAME + " is enabled!");
        PlayerFarmsAPI.bungeeSend("send," + ownerUUID + ",§bサーバーの準備が完了しました。" + "-|-" + "send," + ownerUUID
                + ",§bあなたをサーバーへ転送します。" + "-|-" + "connect," + ownerUUID + "," + Bukkit.getPlayer(UUID.fromString(ownerUUID)).getName());

    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerFarmsAPI.bungeeSend("send," + player.getUniqueId() + ",§7ロビーへ送っています。" + "-|-" + "connect," + player.getUniqueId() + ",hub");
        }

        String onlineServersData = PlayerFarmsAPI.fileReadContents(onlineServersFile);
        String writeData = "";
        for (String line : onlineServersData.split("\n")) {
            if (onlineServersData.contains(ownerUUID)) {
                continue;
            }
            if (line == "\n") {
                continue;
            }
            writeData += line + "\n";
        }
        PlayerFarmsAPI.fileWriteContents(onlineServersFile, writeData);

        fixedStates.setConfig("server.state", "offline");

        PlayerFarmsAPI.bungeeSend(
                "removeServer," + Bukkit.getPlayer(ownerUUID).getName());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (cmd.getName().equalsIgnoreCase("realms")) {
            getLogger().info("Hello, World!");
            return true;
        }
        return false;
    }

}