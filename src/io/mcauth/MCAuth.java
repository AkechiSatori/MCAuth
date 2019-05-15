package io.mcauth;

import io.mcauth.database.SQLite;
import org.bukkit.block.data.type.Switch;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.mcauth.command.CommandManager;
import io.mcauth.database.Database;
import io.mcauth.database.MySQL;
import io.mcauth.player.PlayerListener;
import io.mcauth.player.PlayerListener2;
import io.mcauth.player.PlayerManager;
import io.mcauth.service.MessageService;
import io.mcauth.service.TimeoutKickService;

public class MCAuth extends JavaPlugin {
	private static MCAuth plugin;

	public Database db;
	public PlayerManager pm;
	public CommandManager cm;
	
	public static void main(String[] args) {
		
	}
	
	public void onEnable() {
		plugin = this;
		ConfigManager.loadConfig(this);
		if (!initDB()) {
			getServer().shutdown();
		}
		pm = new PlayerManager();
		cm = new CommandManager();
		cm.initDefaultCommand();
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener2(), this);
		startService();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			switch (command.getName()) {
			case "register":
				this.cm.rCmd.execute((Player) sender, args);
				break;
			case "login":
				this.cm.lCmd.execute((Player) sender, args);
				break;
			default:
			}
		}
		return true;
	}

	public boolean initDB() {
		switch (ConfigManager.database_type) {
			case "MySQL":
				db = new MySQL(ConfigManager.database_host, ConfigManager.database_port, ConfigManager.database_user,
						ConfigManager.database_password, ConfigManager.database_db, ConfigManager.database_table);
				break;
			case "SQLite":
				db = new SQLite(ConfigManager.database_table);
				break;
		}

		try {
			db.init();
			this.db = db;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void startService() {
		MessageService msgService = new MessageService();
		TimeoutKickService tkService = new TimeoutKickService();

		msgService.start();
		tkService.start();
	}

	public static MCAuth getPlugin() {
		return plugin;
	}
}
