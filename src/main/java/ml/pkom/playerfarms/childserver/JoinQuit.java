package ml.pkom.playerfarms.childserver;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ml.pkom.playerfarms.childserver.api.ServerPFConfig;

public class JoinQuit implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        ServerPFConfig.getInstance().setIntConfig("server.onlinePlayers", Bukkit.getOnlinePlayers().size());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        ServerPFConfig.getInstance().setIntConfig("server.onlinePlayers", Bukkit.getOnlinePlayers().size() - 1);
    }
}
