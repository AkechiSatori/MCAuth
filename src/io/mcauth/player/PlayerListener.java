package io.mcauth.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import io.mcauth.ConfigManager;
import io.mcauth.MCAuth;
import io.mcauth.Utils;

public class PlayerListener implements Listener {
	private MCAuth plugin;

	public PlayerListener() {
		this.plugin = MCAuth.getPlugin();
	}

	@EventHandler
	public void onAsyncLogin(AsyncPlayerPreLoginEvent event) {
		PlayerAuth auth = plugin.db.getAuth(event.getName());
		if (auth != null) {
			plugin.pm.addAuth(auth);
		}
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		PlayerAuth auth = plugin.pm.getAuth(player.getName());
		String player_ip = player.getAddress().getHostString();
		int expire_time = ConfigManager.session_expire_time * 60;

		String login_session_message = String.format("§d欢迎回来, §a%s", player.getName());
		String broadcast_message = String.format("§e%s §b加入了游戏", player.getName());
		event.setJoinMessage(broadcast_message);

		if (ConfigManager.session_enable == true) {
			if (auth.getLastip().equals(player_ip) && (auth.getLastlogin() + expire_time) > Utils.getTimestamp()) {
				player.sendMessage(login_session_message);
			}
		}
	}
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		PlayerAuth auth = plugin.pm.getAuth(player.getName());
		String player_ip = event.getAddress().getHostAddress();
		int expire_time = ConfigManager.session_expire_time * 60;
		if (auth == null) {
			auth = plugin.db.getAuth(player.getName());
			if (auth != null) {
				plugin.pm.addAuth(auth);
			} else {
				event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "获取验证数据失败!");
				return;
			}
		}
		if (auth.isRegister()) {
			if (!auth.getRealname().equals(player.getName())) {
				event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "请使用正确大小写的用户名:\n" + auth.getRealname());
				return;
			}
			Bukkit.getOnlinePlayers().forEach((p) -> {
				if (p.getName().equalsIgnoreCase(player.getName())) {
					event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "相同的用户名已在线!");
					return;
				}
			});
			if (ConfigManager.session_enable == true) {
				if (auth.getLastip().equals(player_ip) && (auth.getLastlogin() + expire_time) > Utils.getTimestamp()) {
					auth.setAuth(true);
				}
			}
		}
		auth.setLastip(player_ip);
		auth.setLastlogin(Utils.getTimestamp());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		plugin.pm.removeAuth(plugin.pm.getAuth(event.getPlayer().getName()));
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		plugin.pm.removeAuth(plugin.pm.getAuth(event.getPlayer().getName()));
	}
}
