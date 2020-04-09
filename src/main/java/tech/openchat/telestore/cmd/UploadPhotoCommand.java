package tech.openchat.telestore.cmd;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author vgorin
 * file created on 2020-04-09 20:47
 */

@Component
public class UploadPhotoCommand implements NamedCommand {
    @Override
    public String getDefaultCommandName() {
        return "photo";
    }

    @Override
    public PartialBotApiMethod<Message> process(CommandPayload payload) {
        return new SendMessage()
                .setChatId(payload.getChatId())
                .setText(payload.getArguments().iterator().next());
    }
}
