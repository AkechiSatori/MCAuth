package mcauth.command;

import org.bukkit.entity.Player;

import mcauth.MCAuth;
import mcauth.player.PlayerAuth;
import mcauth.security.Encrypt;

public class LoginCommand {
	private MCAuth plugin;

	public LoginCommand() {
		this.plugin = MCAuth.getPlugin();
	}

	public void execute(Player player, String[] args) {
		PlayerAuth auth = plugin.pm.getAuth(player.getName());
		if (auth.isRegister()) {
			if (args.length < 1) {
				player.sendMessage("§c登录: /l <密码>");
				return;
			}

			if (Encrypt.check(args[0], auth.getPassword(), "SHA-512")) {
				auth.setAuth(true);
				plugin.db.updateSession(player.getName(), auth.getLastip(), auth.getLastlogin());
				player.sendMessage("§a登录成功!");
			} else {
				player.sendMessage("§c密码错误!");
			}
		} else {
			player.sendMessage("§c你还未注册!");
		}
	}
}
