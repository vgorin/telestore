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
class EchoCommand implements NamedCommand {
    static final String DEFAULT_CMD_NAME = "echo";

    @Override
    public String getDefaultCommandName() {
        return DEFAULT_CMD_NAME;
    }

    public String getDescription() {
        return "Echoes back, reversing the letters";
    }

    @Override
    public BotApiMethod<Message> process(CommandPayload payload) {
        return new SendMessage()
                .setChatId(payload.getChatId())
                .setText(StringUtils.reverse(payload.getArguments().iterator().next()));
    }
}
