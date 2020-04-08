package tech.openchat.telestore.cmd;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author vgorin
 * file created on 2020-04-02 21:40
 */

@Getter
class CommandPayload {
    private final int userId;
    private final long chatId;
    private final String commandName; // argument 0 – mandatory
    private final List<String> arguments = new LinkedList<>(); // arguments 1+ – optional

    CommandPayload(int userId, long chatId, String commandName, String... arguments) {
        this.userId = userId;
        this.chatId = chatId;
        this.commandName = commandName;
        addArguments(Arrays.asList(arguments));
    }

    void addArgument(String argument) {
        arguments.add(argument);
    }

    void addArguments(Collection<String> arguments) {
        this.arguments.addAll(arguments);
    }
}
