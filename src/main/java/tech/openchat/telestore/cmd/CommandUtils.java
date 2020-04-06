package tech.openchat.telestore.cmd;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author vgorin
 * file created on 2020-04-05 02:28
 */

class CommandUtils {
    static ReplyKeyboard verticalKeyboard(Iterable<Map.Entry<String, String>> buttons) {
        List<List<InlineKeyboardButton>> keyboard = new LinkedList<>();
        for(Map.Entry<String, String> button: buttons) {
            InlineKeyboardButton btn = new InlineKeyboardButton();
            btn.setCallbackData(button.getKey());
            btn.setText(button.getValue());
            keyboard.add(Collections.singletonList(btn));
        }
        return new InlineKeyboardMarkup(keyboard);
    }

    static ReplyKeyboard verticalKeyboard(Map<String, String> buttons) {
        return verticalKeyboard(buttons.entrySet());
    }

    static ReplyKeyboard singleBtnKeyboard(String callback, String text) {
        InlineKeyboardButton btn = new InlineKeyboardButton();
        btn.setCallbackData(callback);
        btn.setText(text);
        return new InlineKeyboardMarkup(Collections.singletonList(Collections.singletonList(btn)));
    }

}
