package io.mcauth.service;

import org.bukkit.Bukkit;

import io.mcauth.ConfigManager;
import io.mcauth.MCAuth;
import io.mcauth.Utils;
import io.mcauth.player.PlayerAuth;

public class TimeoutKickService {
	public void start() {
		Bukkit.getScheduler().runTaskTimer(MCAuth.getPlugin(), () -> {
			Bukkit.getOnlinePlayers().forEach((player) -> {
				PlayerAuth auth = MCAuth.getPlugin().pm.getAuth(player.getName());
				if (Utils.getTimestamp() - auth.getLastlogin() > ConfigManager.login_timeout) {
					if (!auth.isAuth())
						player.kickPlayer("登录超时");
				}
			});
		}, 20, 20);
	}
}