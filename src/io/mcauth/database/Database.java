package io.mcauth.database;

import io.mcauth.player.PlayerAuth;

public abstract interface Database {
	public abstract void init() throws Exception;

	public abstract PlayerAuth getAuth(String player);

	public abstract boolean saveAuth(PlayerAuth auth);

	public abstract boolean updateSession(String player, String lastip, int lastlogin);
}
