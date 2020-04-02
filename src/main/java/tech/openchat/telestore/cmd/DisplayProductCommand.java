package tech.openchat.telestore.cmd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import tech.openchat.telestore.entity.Product;
import tech.openchat.telestore.service.ProductService;

import javax.persistence.EntityNotFoundException;
import java.util.Iterator;

/**
 * @author vgorin
 * file created on 2020-04-02 21:47
 */

@Slf4j
@Component
public class DisplayProductCommand implements NamedCommand {
    private final ProductService productService;

    public DisplayProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String getDefaultCommandName() {
        return "product";
    }

    @Override
    public BotApiMethod<Message> process(CommandPayload payload) {
        Iterator<String> i = payload.getArguments().iterator();

        if(!i.hasNext()) {
            log.debug("no product ID specified");
            return null;
        }

        try {
            Product product = productService.getProduct(Long.parseLong(i.next()));
            return new SendMessage()
                    .setChatId(payload.getChatId())
                    .setParseMode(ParseMode.MARKDOWN)
                    .setText(String.format(
                            "*%s*\n\n%s\n\n*$%.2f*",
                            product.getTitle(),
                            product.getDescription(),
                            product.getPrice()
                    ));
        }
        catch(NumberFormatException e) {
            log.debug("malformed product ID: integer expected", e);
            return null;
        }
        catch(EntityNotFoundException e) {
            log.debug("product ID cannot be found", e);
            return null;
        }
    }
}
