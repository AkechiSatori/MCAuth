package io.mcauth.player;

public class PlayerAuth {
	private String name;
	private String realname;
	private String password;
	private String lastip;
	private int lastlogin;
	private String uuid;
	private int regtime;
	private boolean register;
	private boolean auth;
	private int retry_count;

	public int getRetryCount() {
		return this.retry_count;
	}

	public void addRetryCount() {
		this.retry_count ++;
	}

	public void setUUID(String uuid) {
		this.uuid = uuid;
	}
	public String getUUID() {
		return this.uuid;
	}

	public void SetRegtime(int regtime) {
		this.regtime = regtime;
	}
	public int getRegtime() {
		return this.regtime;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setRegister(boolean register) {
		this.register = register;
	}

	public boolean isRegister() {
		return this.register;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public boolean isAuth() {
		return this.auth;
	}

	public String getLastip() {
		return lastip;
	}

	public void setLastip(String lastip) {
		this.lastip = lastip;
	}

	public int getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(int lastlogin) {
		this.lastlogin = lastlogin;
	}
}
