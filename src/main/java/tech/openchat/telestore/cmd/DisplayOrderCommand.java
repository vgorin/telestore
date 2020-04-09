package tech.openchat.telestore.cmd;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author vgorin
 * file created on 2020-04-08 13:46
 */

@Component
public class DisplayOrderCommand implements NamedCommand {
    @Override
    public String getDefaultCommandName() {
        return "order";
    }

    @Override
    public PartialBotApiMethod<Message> process(CommandPayload payload) {
        return new SendMessage()
                .setChatId(payload.getChatId())
                .setText("//TODO: implement");
    }
}
