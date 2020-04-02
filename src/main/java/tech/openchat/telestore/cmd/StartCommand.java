package tech.openchat.telestore.cmd;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author vgorin
 * file created on 2020-04-02 21:39
 */

@Component
public class StartCommand implements NamedCommand {
    @Override
    public String getDefaultCommandName() {
        return "start";
    }

    @Override
    public BotApiMethod<Message> process(CommandPayload payload) {
        List<List<InlineKeyboardButton>> keyboard = new LinkedList<>();
        InlineKeyboardButton btn = new InlineKeyboardButton();
        btn.setText("List Products");
        btn.setCallbackData("/products");
        keyboard.add(Collections.singletonList(btn));

        return new SendMessage()
                .setChatId(payload.getChatId())
                .setReplyMarkup(new InlineKeyboardMarkup(keyboard))
                .setText("Welcome!");

    }

}
