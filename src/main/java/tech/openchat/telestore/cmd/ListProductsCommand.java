package tech.openchat.telestore.cmd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import tech.openchat.telestore.entity.Product;
import tech.openchat.telestore.service.ProductService;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author vgorin
 * file created on 2020-03-31 15:38
 */

@Slf4j
@Component
class ListProductsCommand implements NamedCommand {
    private final ProductService productService;

    public ListProductsCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String getDefaultCommandName() {
        return "products";
    }

    public String getDescription() {
        return "Lists all the products in the catalog";
    }

    @Override
    public BotApiMethod<Message> process(CommandPayload payload) {
        int page = 0;
        int size = 10;

        Iterator<String> i = payload.getArguments().iterator();
        try {
            if(i.hasNext()) {
                page = Integer.parseInt(i.next());
            }
        }
        catch(NumberFormatException e) {
            log.debug("malformed page number format: integer expected", e);
        }

        try {
            if(i.hasNext()) {
                size = Integer.parseInt(i.next());
            }
        }
        catch(NumberFormatException e) {
            log.debug("malformed page size format: integer expected", e);
        }

        Pageable pageable = Pageable.unpaged();
        try {
            pageable = PageRequest.of(page, size);
        }
        catch(IllegalArgumentException e) {
            log.debug("wrong page settings: " + e.getMessage(), e);
        }

        Page<Product> products = productService.listAllProducts(pageable);

        List<List<InlineKeyboardButton>> keyboard = new LinkedList<>();
        for(Product product: products) {
            InlineKeyboardButton btn = new InlineKeyboardButton();
            btn.setText(product.getTitle());
            btn.setCallbackData("/product " + product.getId());
            keyboard.add(Collections.singletonList(btn));
        }

        return new SendMessage()
                .setChatId(payload.getChatId())
                .setReplyMarkup(new InlineKeyboardMarkup(keyboard))
                .setText("Products:");
    }
}
