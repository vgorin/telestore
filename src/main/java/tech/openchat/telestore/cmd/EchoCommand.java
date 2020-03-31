package tech.openchat.telestore.cmd;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author vgorin
 * file created on 2020-03-31 16:25
 */

@Slf4j
@Component
public class EchoCommand implements Command {
    static final String NAME = "echo";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return "Echoes back, reversing the letters";
    }

    @Override
    public BotApiMethod<Message> process(Update update) {
        return new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(StringUtils.reverse(update.getMessage().getText()));
    }
}
