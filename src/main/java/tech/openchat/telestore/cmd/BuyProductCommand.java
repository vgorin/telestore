package tech.openchat.telestore.cmd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import tech.openchat.telestore.entity.Order;
import tech.openchat.telestore.entity.Product;
import tech.openchat.telestore.service.OrderService;
import tech.openchat.telestore.service.ProductService;
import tech.openchat.telestore.service.WalletService;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeMap;

import static tech.openchat.telestore.cmd.CommandUtils.verticalKeyboard;

/**
 * @author vgorin
 * file created on 2020-04-05 04:39
 */

@Slf4j
@Component
public class BuyProductCommand implements NamedCommand {
    private final ResourceBundleMessageSource messageSource;
    private final ProductService productService;
    private final OrderService orderService;
    private final WalletService walletService;

    public BuyProductCommand(
            ResourceBundleMessageSource messageSource,
            ProductService productService,
            OrderService orderService,
            WalletService walletService
    ) {
        this.messageSource = messageSource;
        this.productService = productService;
        this.orderService = orderService;
        this.walletService = walletService;
    }

    @Override
    public String getDefaultCommandName() {
        return "buy";
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

            Order order = new Order();
            order.setProduct(product);
            order.setDate(new Date());
            order.setPrice(product.getPrice());
            order.setWallet(walletService.createNewWallet());

            return new SendMessage()
                    .setChatId(payload.getChatId())
                    .setParseMode(ParseMode.MARKDOWN)
                    .setText(String.format(
                            messageSource.getMessage("buy.text", null, Locale.ENGLISH),
                            order.getPrice(),
                            order.getWallet().getAddressHex()
                    ))
                    .setReplyMarkup(verticalKeyboard(new TreeMap<String, String>() {{
                        put("/orders", messageSource.getMessage("buy.buttons.my_orders", null, Locale.ENGLISH));
                        put("/products", messageSource.getMessage("buy.buttons.back_to_products", null, Locale.ENGLISH));
                        put("/start", messageSource.getMessage("buy.buttons.back_to_home", null, Locale.ENGLISH));
                    }}));
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
