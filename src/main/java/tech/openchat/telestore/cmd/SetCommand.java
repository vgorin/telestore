package tech.openchat.telestore.cmd;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import tech.openchat.telestore.service.UserSettingsService;

import javax.persistence.EntityNotFoundException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.NoSuchElementException;

import static tech.openchat.telestore.cmd.CommandUtils.verticalKeyboard;

/**
 * @author vgorin
 * file created on 2020-04-14 19:48
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class SetCommand implements NamedCommand {
    private final ResourceBundleMessageSource messageSource;
    private final UserSettingsService userSettingsService;

    @Override
    public String getDefaultCommandName() {
        return "set";
    }

    @Override
    public PartialBotApiMethod<Message> process(CommandPayload payload) {
        Iterator<String> i = payload.getArguments().iterator();

        ReplyKeyboard keyboard = verticalKeyboard(new LinkedHashMap<String, String>(){{
            put("/settings", messageSource.getMessage("set.buttons.back_to_settings", null, Locale.ENGLISH));
            put("/start", messageSource.getMessage("set.buttons.back_to_home", null, Locale.ENGLISH));
        }});

        try {
            String key = i.next();
            String value = i.next();

            switch(key) {
                case "service_area": {
                    try {
                        int serviceAreaId = Integer.parseInt(value);
                        try {
                            userSettingsService.setServiceArea(payload.getUserId(), serviceAreaId);
                            return new SendMessage()
                                    .setChatId(payload.getChatId())
                                    .setText(messageSource.getMessage("set.texts.service_area_updated", null, Locale.ENGLISH))
                                    .setReplyMarkup(keyboard);
                        }
                        catch(EntityNotFoundException e) {
                            log.debug("unknown service area ID: " + serviceAreaId);
                        }
                    }
                    catch(NumberFormatException e) {
                        log.debug("malformed service area ID: integer expected");
                    }
                }
                default: {
                    log.debug("unknown setting name {}", key);
                }
            }
        }
        catch(NoSuchElementException e) {
            log.debug("malformed command: not enough arguments");
        }

        return new SendMessage()
                .setChatId(payload.getChatId())
                .setText(messageSource.getMessage("set.texts.usage", null, Locale.ENGLISH))
                .setReplyMarkup(keyboard);
    }
}
