package io.mcauth;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager {
	public static FileConfiguration config;

	public static String database_host;
	public static int database_port;
	public static String database_user;
	public static String database_password;
	public static String database_db;
	public static String database_table;
	public static String database_type;

	public static boolean session_enable;
	public static int session_expire_time;
	public static int login_timeout;
	public static int message_interval;

	public static void loadConfig(Plugin plugin) {
		plugin.saveDefaultConfig();
		config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));

		database_host = config.getString("Database.Host");
		database_port = config.getInt("Database.Port");
		database_user = config.getString("Database.User");
		database_password = config.getString("Database.Password");
		database_db = config.getString("Database.DB");
		database_table = config.getString("Database.Table");

		database_type = config.getString("Database.Type");

		session_enable = config.getBoolean("SessionEnable", true);
		session_expire_time = config.getInt("SessionExpireTime", 60);
		login_timeout = config.getInt("LoginTimeout", 60);
		message_interval = config.getInt("MessageInterval", 3);
	}
}
