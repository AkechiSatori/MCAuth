package mcauth.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mcauth.player.PlayerAuth;

public class MySQL implements Database {
	private String host;
	private int port;
	private String user;
	private String pass;
	private String db;
	private String table;

	private Connection conn;

	public MySQL(String host, int port, String user, String pass, String db, String table) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
		this.db = db;
		this.table = table;
	}

	public void init() throws Exception {
		String url = String.format(
				"jdbc:mysql://%s:%s/%s?autoReconnect=true&characterEncoding=utf-8&encoding=utf-8&useUnicode=true", host,
				port, db);
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url, user, pass);
		conn.createStatement().execute("CREATE TABLE IF NOT EXISTS `" + table
				+ "` (`id` int(11) NOT NULL AUTO_INCREMENT,`name` varchar(16) NOT NULL,`password` varchar(255) NOT NULL,`realname` varchar(16) NOT NULL,`lastip` varchar(16) NOT NULL,`lastlogin` int(11) NOT NULL,PRIMARY KEY (`id`),KEY `name` (`name`)) CHARSET=UTF8;");
		this.conn = conn;
	}

	public PlayerAuth getAuth(String player) {
		String sql = "SELECT * FROM " + table + " WHERE name=?;";

		PlayerAuth auth = new PlayerAuth();
		auth.setName(player.toLowerCase());

		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, auth.getName());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				auth.setRegister(true);
				auth.setPassword(rs.getString("password"));
				auth.setRealname(rs.getString("realname"));
				auth.setLastip(rs.getString("lastip"));
				auth.setLastlogin(rs.getInt("lastlogin"));
			} else {
				auth.setRegister(false);
			}
			return auth;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean saveAuth(PlayerAuth auth) {
		String sql = "INSERT INTO " + table + "(name,password,realname,lastip,lastlogin) VALUES (?,?,?,?,?);";

		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, auth.getName());
			pst.setString(2, auth.getPassword());
			pst.setString(3, auth.getRealname());
			pst.setString(4, auth.getLastip());
			pst.setInt(5, auth.getLastlogin());
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateSession(String player, String lastip, int lastlogin) {
		String sql = "UPDATE " + table + " SET lastip=?,lastlogin=? WHERE name=?;";

		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, lastip);
			pst.setInt(2, lastlogin);
			pst.setString(3, player.toLowerCase());
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
