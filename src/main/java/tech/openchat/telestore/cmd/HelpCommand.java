package tech.openchat.telestore.cmd;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author vgorin
 * file created on 2020-03-31 16:24
 */

@Component
public class HelpCommand implements Command {
    static final String NAME = "help";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return "Displays Help";
    }

    @Override
    public BotApiMethod<Message> process(Update update) {
        return new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("// TODO: display help"); // TODO: display help
    }
}
