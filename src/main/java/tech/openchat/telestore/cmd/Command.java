package tech.openchat.telestore.cmd;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author vgorin
 * file created on 2020-03-31 12:40
 */

public interface Command {
    String getName();
    String getDescription();
    BotApiMethod<Message> process(Update update);
}
