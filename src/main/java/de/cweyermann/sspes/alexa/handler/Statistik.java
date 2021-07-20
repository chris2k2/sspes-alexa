package de.cweyermann.sspes.alexa.handler;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class Statistik implements RequestHandler {

    public static final String PATTERN = "Du hast {0}-mal gewonnen, {1}-mal unentschieden gespielt und {2}-mal verloren.";

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("Statistik"));
    }

    @Override
    public Optional<Response> handle(HandlerInput in) {
        AttributesManager attributesManager = in.getAttributesManager();
        Map<String, Object> attributes = attributesManager.getPersistentAttributes();

        BigDecimal won = (BigDecimal) attributes.getOrDefault("won", BigDecimal.ZERO);
        BigDecimal draw = (BigDecimal) attributes.getOrDefault("draw", BigDecimal.ZERO);
        BigDecimal lost = (BigDecimal) attributes.getOrDefault("lost", BigDecimal.ZERO);

        return in
                .getResponseBuilder()
                .withSpeech(MessageFormat.format(PATTERN, won, draw, lost))
                .withShouldEndSession(true)
                .build();
    }
}
