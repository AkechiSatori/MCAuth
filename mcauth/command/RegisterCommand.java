package mcauth.command;

import org.bukkit.entity.Player;

import mcauth.MCAuth;
import mcauth.player.PlayerAuth;
import mcauth.security.Encrypt;

public class RegisterCommand {
	private MCAuth plugin;

	public RegisterCommand() {
		this.plugin = MCAuth.getPlugin();
	}

	public void execute(Player player, String[] args) {
		PlayerAuth auth = plugin.pm.getAuth(player.getName());
		if (!auth.isRegister()) {
			if (args.length < 2) {
				player.sendMessage("§c注册: /reg <密码> <确认密码>");
				return;
			}

			String password = args[0];
			if (!password.equals(args[1])) {
				player.sendMessage("§c两次输入的密码不一样!");
				return;
			}
			if (password.length() < 6 || password.length() > 20) {
				player.sendMessage("§c密码太长或太短(6-20位)");
				return;
			}

			auth.setPassword(Encrypt.encrypt(password, "SHA-512"));
			auth.setRealname(player.getName());
			auth.setAuth(true);
			auth.setRegister(true);
			plugin.db.saveAuth(auth);
			player.sendMessage("§a注册成功!");
		} else {
			player.sendMessage("§c你已经注册过了!");
		}
	}
}
