package mcauth.command;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
	private List<String> wlc = new ArrayList<String>();

	public RegisterCommand rCmd;
	public LoginCommand lCmd;

	public void initDefaultCommand() {
		rCmd = new RegisterCommand();
		lCmd = new LoginCommand();

		addWhitelistCommand("register");
		addWhitelistCommand("reg");
		addWhitelistCommand("login");
		addWhitelistCommand("l");
	}

	public void addWhitelistCommand(String command) {
		wlc.add(command.toLowerCase());
	}

	public void removeWhitelistCommand(String command) {
		wlc.remove(command.toLowerCase());
	}

	public boolean isAllowBeforeAuth(String command) {
		for (String c : wlc) {
			if (command.toLowerCase().startsWith("/" + c + " "))
				return true;
		}
		return false;
	}
}
