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
    private final Map<String, Command> processors = new ConcurrentHashMap<>();

    public CommandProcessor(Collection<NamedCommand> processors) {
        for(NamedCommand processor: processors) {
            this.processors.put(processor.getDefaultCommandName(), processor);
        }
    }

    public BotApiMethod<Message> process(Update update) {
        CommandPayload payload = extractPayload(update);

        if(payload == null) {
            log.trace("unsupported update type, skipping update {}", update);
            return null;
        }

        log.trace("processing command {} {}", payload.getCommandName(), payload.getArguments());

        Command command = processors.get(payload.getCommandName());
        if(command == null) {
            log.trace("no processor found for command {}", payload.getCommandName());
            command = processors.get(HelpCommand.DEFAULT_CMD_NAME);
        }

        return command.process(payload);
    }

    private static CommandPayload extractPayload(Update update) {
        if(update.hasMessage()
                && update.getMessage().getChatId() != null
                && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            if(!text.startsWith("/")) {
                return new CommandPayload(
                        update.getMessage().getChatId(),
                        EchoCommand.DEFAULT_CMD_NAME,
                        text
                );
            }

            return buildFrom(
                    update.getMessage().getChatId(),
                    text
            );
        }

        if(update.hasCallbackQuery()
                && update.getCallbackQuery().getMessage() != null
                && update.getCallbackQuery().getMessage().getChatId() != null
                && update.getCallbackQuery().getData() != null
                && update.getCallbackQuery().getData().startsWith("/")) {

            return buildFrom(
                    update.getCallbackQuery().getMessage().getChatId(),
                    update.getCallbackQuery().getData()
            );
        }

        return null;
    }

    private static CommandPayload buildFrom(long chatId, String text) {
        StringTokenizer tokenizer = new StringTokenizer(text.substring(1));
        CommandPayload payload = new CommandPayload(
                chatId,
                tokenizer.nextToken()
        );

        while(tokenizer.hasMoreTokens()) {
            payload.addArgument(tokenizer.nextToken());
        }

        return payload;
    }

}
