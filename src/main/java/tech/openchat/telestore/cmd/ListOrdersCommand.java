package tech.openchat.telestore.cmd;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import tech.openchat.telestore.entity.Order;
import tech.openchat.telestore.entity.OrderState;
import tech.openchat.telestore.service.OrderService;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static tech.openchat.telestore.cmd.CommandUtils.singleBtnKeyboard;
import static tech.openchat.telestore.cmd.CommandUtils.verticalKeyboard;

/**
 * @author vgorin
 * file created on 2020-04-07 14:42
 */

@Slf4j
@Component
public class ListOrdersCommand implements NamedCommand {
    private final ResourceBundleMessageSource messageSource;
    private final OrderService orderService;

    public ListOrdersCommand(ResourceBundleMessageSource messageSource, OrderService orderService) {
        this.messageSource = messageSource;
        this.orderService = orderService;
    }

    @Override
    public String getDefaultCommandName() {
        return "orders";
    }

    @Override
    public PartialBotApiMethod<Message> process(CommandPayload payload) {
        Iterator<String> i = payload.getArguments().iterator();

        if(i.hasNext()) {
            String subCommand = i.next();
            try {
                OrderState displayState = OrderState.valueOf(subCommand);

                Pageable pageable = Pageable.unpaged(); // TODO: pageable

                Page<Order> orders = orderService.listOrders(payload.getUserId(), displayState, pageable);

                if(orders.isEmpty()) {
                    return new SendMessage()
                            .setChatId(payload.getChatId())
                            .setText(messageSource.getMessage("orders.text.no_" + displayState, null, Locale.ENGLISH))
                            .setReplyMarkup(singleBtnKeyboard(
                                    "/start",
                                    messageSource.getMessage("orders.buttons.back_to_home", null, Locale.ENGLISH)
                            ));
                }

                return new SendMessage()
                        .setChatId(payload.getChatId())
                        .setText(messageSource.getMessage("orders.text." + displayState, null, Locale.ENGLISH))
                        .setReplyMarkup(verticalKeyboard(orders.stream().map(order -> new ImmutablePair<>(
                                "/order " + order.getId(),
                                order.getProduct().getTitle()
                        )).collect(Collectors.toList())));

            }
            catch(IllegalArgumentException e) {
                log.debug("unrecognized argument " + subCommand);
            }
        }

        List<OrderState> states = orderService.listOrderStates(payload.getUserId());

        if(states.isEmpty()) {
            return new SendMessage()
                    .setChatId(payload.getChatId())
                    .setText(messageSource.getMessage("orders.text.no_orders", null, Locale.ENGLISH))
                    .setReplyMarkup(singleBtnKeyboard(
                            "/start",
                            messageSource.getMessage("orders.buttons.back_to_home", null, Locale.ENGLISH)
                    ));
        }

        if(states.size() == 1) {
            payload.addArgument(states.iterator().next().toString());
            return process(payload);
        }

        return new SendMessage()
                .setChatId(payload.getChatId())
                .setText(messageSource.getMessage("orders.text", null, Locale.ENGLISH))
                .setReplyMarkup(verticalKeyboard(
                        Arrays.stream(OrderState.values())
                                .filter(states::contains)
                                .map(state -> new ImmutablePair<>(
                                        "/orders " + state,
                                        messageSource.getMessage("orders.buttons." + state, null, Locale.ENGLISH)
                                ))
                                .collect(Collectors.toList())
                ));
    }
}
