package tech.openchat.telestore.cmd;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import tech.openchat.telestore.entity.Product;
import tech.openchat.telestore.service.ProductService;

import java.util.Iterator;
import java.util.Locale;
import java.util.stream.Collectors;

import static tech.openchat.telestore.cmd.CommandUtils.verticalKeyboard;

/**
 * @author vgorin
 * file created on 2020-03-31 15:38
 */

@Slf4j
@Component
@RequiredArgsConstructor
class ListProductsCommand implements NamedCommand {
    private final ResourceBundleMessageSource messageSource;
    private final ProductService productService;

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

        return new SendMessage()
                .setChatId(payload.getChatId())
                .setText(messageSource.getMessage("products.text", null, Locale.ENGLISH))
                .setReplyMarkup(verticalKeyboard(products.stream().map(product -> new ImmutablePair<>(
                        "/product " + product.getId(),
                        product.getTitle()
                )).collect(Collectors.toList())));
    }
}
