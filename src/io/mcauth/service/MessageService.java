package io.mcauth.service;

import org.bukkit.Bukkit;

import io.mcauth.ConfigManager;
import io.mcauth.MCAuth;
import io.mcauth.player.PlayerAuth;

public class MessageService {
	public void start() {
		Bukkit.getScheduler().runTaskTimer(MCAuth.getPlugin(), () -> {
			Bukkit.getOnlinePlayers().forEach((player) -> {
				PlayerAuth auth = MCAuth.getPlugin().pm.getAuth(player.getName());
				if (!auth.isAuth()) {
					if (auth.isRegister()) {
						player.sendMessage("§f请使用 /l <密码> 进行登录");
					} else {
						player.sendMessage("§f请使用 /reg <密码> <确认密码> 进行注册");
					}
				}
			});
		}, ConfigManager.message_interval * 20, ConfigManager.message_interval * 20);
	}
}
