package tech.openchat.telestore.cmd;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import tech.openchat.telestore.entity.Product;
import tech.openchat.telestore.service.ProductService;

import javax.persistence.EntityNotFoundException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;

import static tech.openchat.telestore.cmd.CommandUtils.verticalKeyboard;

/**
 * @author vgorin
 * file created on 2020-04-02 21:47
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class DisplayProductCommand implements NamedCommand {
    private final ResourceBundleMessageSource messageSource;
    private final ProductService productService;

    @Override
    public String getDefaultCommandName() {
        return "product";
    }

    @Override
    public PartialBotApiMethod<Message> process(CommandPayload payload) {
        Iterator<String> i = payload.getArguments().iterator();

        if(!i.hasNext()) {
            log.debug("no product ID specified");
            return null;
        }

        try {
            Product product = productService.getProduct(Long.parseLong(i.next()));

            String text = String.format(
                    messageSource.getMessage("product.text", null, Locale.ENGLISH),
                    product.getTitle(),
                    product.getDescription(),
                    product.getPrice()
            );

            ReplyKeyboard keyboard;
            if(i.hasNext() && "--order".equals(i.next()) && i.hasNext()) {
                keyboard = verticalKeyboard(new LinkedHashMap<String, String>() {{
                    put("/order " + i.next(), messageSource.getMessage("product.buttons.back_to_order", null, Locale.ENGLISH));
                    put("/orders", messageSource.getMessage("product.buttons.my_orders", null, Locale.ENGLISH));
                }});
            }
            else {
                keyboard = verticalKeyboard(new LinkedHashMap<String, String>() {{
                    put("/buy " + product.getId(), String.format(
                            messageSource.getMessage("product.buttons.buy", null, Locale.ENGLISH),
                            product.getPrice()
                    ));
                    put("/products", messageSource.getMessage("product.buttons.back_to_products", null, Locale.ENGLISH));
                    put("/start", messageSource.getMessage("product.buttons.back_to_home", null, Locale.ENGLISH));
                }});
            }

            if(product.getPicture() != null && product.getPicture().getUrl() != null) {
                return new SendPhoto()
                        .setChatId(payload.getChatId())
                        .setPhoto(product.getPicture().getUrl())
                        .setParseMode(ParseMode.MARKDOWN)
                        .setCaption(text)
                        .setReplyMarkup(keyboard);
            }

            return new SendMessage()
                    .setChatId(payload.getChatId())
                    .setParseMode(ParseMode.MARKDOWN)
                    .setText(text)
                    .setReplyMarkup(keyboard);
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
