package io.mcauth.command;

import io.mcauth.ConfigManager;
import org.bukkit.entity.Player;

import io.mcauth.MCAuth;
import io.mcauth.player.PlayerAuth;
import io.mcauth.security.Encrypt;

public class LoginCommand {
	private MCAuth plugin;

	public LoginCommand() {
		this.plugin = MCAuth.getPlugin();
	}

	public void execute(Player player, String[] args) {
		PlayerAuth auth = plugin.pm.getAuth(player.getName());
		if (auth.isRegister()) {
			if (args.length < 1) {
				player.sendMessage("§c登录: /login <密码>");
				return;
			}

			if (auth.getRetryCount() >= ConfigManager.wrong_password_retry) {
				player.kickPlayer("§c密码错误次数过多");
			}

			if (auth.isAuth()) {
				player.sendMessage("§3重复登录");
				return;
			}

			if (Encrypt.check(args[0], auth.getPassword(), "SHA-512")) {
				auth.setAuth(true);
				plugin.db.updateSession(player.getName(), auth.getLastip(), auth.getLastlogin());
				player.sendMessage("§a登录成功!");
			} else {
				auth.addRetryCount();
				player.sendMessage("§c密码错误!");
			}
		} else {
			player.sendMessage("§c你还未注册!");
		}
	}
}
