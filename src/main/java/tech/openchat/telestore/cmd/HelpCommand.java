package tech.openchat.telestore.cmd;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;

/**
 * @author vgorin
 * file created on 2020-03-31 16:24
 */

@Component
class HelpCommand implements NamedCommand {
    static final String DEFAULT_CMD_NAME = "help";

    private final ResourceBundleMessageSource messageSource;

    HelpCommand(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @Override
    public String getDefaultCommandName() {
        return DEFAULT_CMD_NAME;
    }

    public String getDescription() {
        return "Displays Help";
    }

    @Override
    public BotApiMethod<Message> process(CommandPayload payload) {
        return new SendMessage()
                .setChatId(payload.getChatId())
                .setText(messageSource.getMessage("help.text", null, Locale.ENGLISH)); // TODO: display help
    }
}
