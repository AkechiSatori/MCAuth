package io.mcauth.player;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
	private List<PlayerAuth> auths = new ArrayList<PlayerAuth>();

	public void addAuth(PlayerAuth auth) {
		synchronized (auths) {
			auths.add(auth);
		}
	}

	public PlayerAuth getAuth(String player) {
		synchronized (auths) {
			for (PlayerAuth auth : auths) {
				if (auth.getName().contains(player.toLowerCase()))
					return auth;
			}
		}
		return null;
	}

	public void removeAuth(PlayerAuth auth) {
		synchronized (auths) {
			auths.remove(auth);
		}
	}
}
