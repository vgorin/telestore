package tech.openchat.telestore.cmd;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author vgorin
 * file created on 2020-04-02 19:37
 */

public interface Command {
    PartialBotApiMethod<Message> process(CommandPayload payload);
}
