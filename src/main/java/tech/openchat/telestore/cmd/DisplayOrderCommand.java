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
import tech.openchat.telestore.entity.Order;
import tech.openchat.telestore.service.OrderService;

import javax.persistence.EntityNotFoundException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;

import static tech.openchat.telestore.cmd.CommandUtils.verticalKeyboard;

/**
 * @author vgorin
 * file created on 2020-04-08 13:46
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class DisplayOrderCommand implements NamedCommand {
    private final ResourceBundleMessageSource messageSource;
    private final OrderService orderService;

    @Override
    public String getDefaultCommandName() {
        return "order";
    }

    @Override
    public PartialBotApiMethod<Message> process(CommandPayload payload) {
        Iterator<String> i = payload.getArguments().iterator();

        if(!i.hasNext()) {
            log.debug("no order ID specified");
            return null;
        }

        try {
            Order order = orderService.getOrder(payload.getUserId(), Integer.parseInt(i.next()));

            return new SendMessage()
                    .setChatId(payload.getChatId())
                    .setParseMode(ParseMode.MARKDOWN)
                    .setText(String.format(
                            messageSource.getMessage("order.text", null, Locale.ENGLISH),
                            order.getProduct().getTitle(),
                            messageSource.getMessage("order.states." + order.getState(), null, Locale.ENGLISH),
                            order.getPrice(),
                            order.getWallet().getAddress(),
                            order.getCreated()
                    ))
                    .setReplyMarkup(verticalKeyboard(new LinkedHashMap<String, String>() {{
                        put("/product " + order.getProduct().getId() + " --order " + order.getId(),
                                messageSource.getMessage("order.buttons.product_details", null, Locale.ENGLISH));
                        put("/orders", messageSource.getMessage("order.buttons.back_to_orders", null, Locale.ENGLISH));
                        put("/start", messageSource.getMessage("order.buttons.back_to_home", null, Locale.ENGLISH));
                    }}));
        }
        catch(NumberFormatException e) {
            log.debug("malformed order ID: integer expected", e);
            return null;
        }
        catch(EntityNotFoundException e) {
            log.debug("order ID cannot be found", e);
            return null;
        }
    }
}
