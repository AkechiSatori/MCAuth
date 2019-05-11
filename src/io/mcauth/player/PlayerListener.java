package io.mcauth.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
	public void onLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		PlayerAuth auth = plugin.pm.getAuth(player.getName());
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
			if (ConfigManager.session_enable == true &&auth.getLastip().equals(event.getAddress().getHostAddress())
					&& (auth.getLastlogin() + (ConfigManager.session_expire_time * 60)) > Utils.getTimestamp()) {
				auth.setAuth(true);
			} else {
				Bukkit.getOnlinePlayers().forEach((p) -> {
					if (p.getName().equalsIgnoreCase(player.getName())) {
						event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "相同的用户名已在线!");
						return;
					}
				});
			}
		}
		auth.setLastip(event.getAddress().getHostAddress());
		auth.setLastlogin(Utils.getTimestamp());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		plugin.pm.removeAuth(plugin.pm.getAuth(event.getPlayer().getName()));
	}

}
