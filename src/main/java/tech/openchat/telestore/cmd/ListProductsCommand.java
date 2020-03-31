package tech.openchat.telestore.cmd;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author vgorin
 * file created on 2020-03-31 15:38
 */

@Component
public class ListProductsCommand implements Command {
    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Lists all the products in the catalog";
    }

    @Override
    public BotApiMethod<Message> process(Update update) {
        return new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("// TODO: list products"); // TODO: list products
    }
}
