package de.cweyermann.sspes.alexa.handler;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import de.cweyermann.sspes.api.MatchResult;
import de.cweyermann.sspes.api.SspesAPI;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class Spielen implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("Spielen"));
    }

    @Override
    public Optional<Response> handle(HandlerInput in) {
        IntentRequest intent = (IntentRequest) in.getRequestEnvelope().getRequest();
        Map<String, Slot> slots = intent.getIntent().getSlots();

        String yourChoice = slots.get("option").getValue();
        SspesAPI api = new SspesAPI();

        if (!api.getAllowed().contains(yourChoice.toLowerCase())) {
            slots.remove("option");

            return in
                    .getResponseBuilder()
                    .addElicitSlotDirective("option", intent.getIntent())
                    .withSpeech("Eingabe leider ungültig. Nur Schere, Stein, Papier, Echse, Spock. ")
                    .build();
        }

        MatchResult matchResult = api.playUnrankedAgainstRandom(yourChoice);

        String speechResult = MessageFormat.format("Du hast gegen {0} gespielt. Du wähltest {1}, {0} {2}. Du hast {3}",
                matchResult.getOpponentName(),
                yourChoice,
                matchResult.getOpponentChoice(),
                matchResult.getResult().getAussprache());


        AttributesManager attributesManager = in.getAttributesManager();
        Map<String, Object> attributes = attributesManager.getPersistentAttributes();

        BigDecimal won = (BigDecimal) attributes.getOrDefault("won", BigDecimal.ZERO);
        BigDecimal draw = (BigDecimal) attributes.getOrDefault("draw", BigDecimal.ZERO);
        BigDecimal lost = (BigDecimal) attributes.getOrDefault("lost", BigDecimal.ZERO);

        if (matchResult.getResult() == MatchResult.Result.WON) {
            won = won.add(BigDecimal.ONE);
        } else if (matchResult.getResult() == MatchResult.Result.DRAW) {
            draw = draw.add(BigDecimal.ONE);
        } else {
            lost = lost.add(BigDecimal.ONE);
        }

        attributes.put("won", won);
        attributes.put("draw", draw);
        attributes.put("lost", lost);
        attributesManager.setPersistentAttributes(attributes);
        attributesManager.savePersistentAttributes(); //nur bei persistent notwendig

        return in
                .getResponseBuilder()
                .withSpeech(speechResult)
                .withShouldEndSession(true)
                .build();

    }
}
