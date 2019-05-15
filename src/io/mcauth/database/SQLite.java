package io.mcauth.database;

import io.mcauth.player.PlayerAuth;
import org.bukkit.Bukkit;

import java.sql.*;

public class SQLite implements Database {
    private String table;

    private Connection conn;

    public SQLite(String table) {
        this.table = table;
    }
    public void init() throws Exception {
        try {
            String folder = Bukkit.getPluginManager().getPlugin("MCAuth").getDataFolder().getPath();
            String url = String.format("jdbc:sqlite:%s/mcauth.db", folder);
            Connection conn = DriverManager.getConnection(url);

            String create_db = String.format("CREATE TABLE IF NOT EXISTS `%s` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "`name` varchar(32) NOT NULL," +
                    " `uuid` varchar(40) NOT NULL," +
                    "`password` varchar(255) NOT NULL," +
                    "`realname` varchar(32) NOT NULL," +
                    "`lastip` varchar(16) NOT NULL," +
                    "`regtime` int(10) NOT NULL," +
                    "`lastlogin` int(10) NOT NULL);", table);

            String create_index = String.format("CREATE INDEX IF NOT EXISTS id ON %s(id);",table);
            conn.createStatement().execute(create_db);
            conn.createStatement().execute(create_index);

            this.conn = conn;
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        String sql = "INSERT INTO " + table + "(name,password,realname,lastip,uuid,regtime,lastlogin) VALUES (?,?,?,?,?,?,?);";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, auth.getName());
            pst.setString(2, auth.getPassword());
            pst.setString(3, auth.getRealname());
            pst.setString(4, auth.getLastip());
            pst.setString(5, auth.getUUID());
            pst.setInt(6,auth.getRegtime());
            pst.setInt(7,auth.getLastlogin());
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
