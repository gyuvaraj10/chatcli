package net.uni.chat.cli.poc;

import java.util.Arrays;

public class CommandRunner {

    private Server server;
    private ClientReaderWriter crw;

    public CommandRunner(Server server, ClientReaderWriter crw) {
        this.server = server;
        this.crw = crw;
    }

    public void runCommand(String message) {
        switch (message.split(":")[1]) {
            case "show users": {
                server.broadCastToRequestedUser(this.crw);
                break;
            }
            case "bye": {
                String userName = message.split(":")[0];
                server.cleanUser(userName);
                server.broadCastMessage(userName+ " Left the group", this.crw);
                server.assignCoordinatorRole();
                break;
            }
            default: break;
        }
    }

    public boolean isACommand(String message) {
        return Arrays.stream(Command.values()).anyMatch(x->x.command.equals(message));
    }

    enum Command {
        SHOW_USERS("show users"),
        BYE("bye");
        private final String command;
        Command(String command) {
            this.command = command;
        }
        @Override
        public String toString() {
            return this.command;
        }
    }
}
