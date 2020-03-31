package tech.openchat.telestore.cmd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collection;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author vgorin
 * file created on 2020-03-31 12:41
 */

@Slf4j
@Component
public class CommandProcessor {
    private final Map<String, Command> commands = new ConcurrentHashMap<>();

    public CommandProcessor(Collection<Command> commands) {
        for(Command command: commands) {
            this.commands.put(command.getName(), command);
        }
    }

    public BotApiMethod<Message> process(Update update) {
        String commandName = extractCommandNameFrom(update);
        log.trace("processing command " + commandName);

        Command command = commands.get(commandName);
        if(command == null) {
            command = commands.get(HelpCommand.NAME);
        }

        return command.process(update);
    }

    private String extractCommandNameFrom(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();

            if(!text.startsWith("/")) {
                return EchoCommand.NAME;
            }

            return new StringTokenizer(text.substring(1)).nextToken();
        }

        return HelpCommand.NAME;
    }
}
