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
    private final long chatId;
    private final String commandName;
    private final List<String> arguments = new LinkedList<>();

    CommandPayload(long chatId, String commandName, String... arguments) {
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
