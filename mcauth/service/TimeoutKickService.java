package mcauth.service;

import org.bukkit.Bukkit;

import mcauth.ConfigManager;
import mcauth.MCAuth;
import mcauth.Utils;
import mcauth.player.PlayerAuth;

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