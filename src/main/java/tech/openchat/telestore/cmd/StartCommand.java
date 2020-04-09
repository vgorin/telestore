package tech.openchat.telestore.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.LinkedHashMap;
import java.util.Locale;

import static tech.openchat.telestore.cmd.CommandUtils.verticalKeyboard;

/**
 * @author vgorin
 * file created on 2020-04-02 21:39
 */

@Component
@RequiredArgsConstructor
public class StartCommand implements NamedCommand {
    private final ResourceBundleMessageSource messageSource;

    @Override
    public String getDefaultCommandName() {
        return "start";
    }

    @Override
    public BotApiMethod<Message> process(CommandPayload payload) {
        return new SendMessage()
                .setChatId(payload.getChatId())
                .setParseMode(ParseMode.MARKDOWN)
                .setText(messageSource.getMessage("start.text", null, Locale.ENGLISH))
                .setReplyMarkup(verticalKeyboard(new LinkedHashMap<String, String>() {{
                    put("/products", messageSource.getMessage("start.buttons.list_products", null, Locale.ENGLISH));
                    put("/orders", messageSource.getMessage("start.buttons.my_orders", null, Locale.ENGLISH));
                }}));
    }

}
