package tech.openchat.telestore.cmd;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author vgorin
 * file created on 2020-04-02 19:37
 */

public interface Command {
    BotApiMethod<Message> process(CommandPayload payload);
}
