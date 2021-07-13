package de.cweyermann.sspes.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import de.cweyermann.sspes.api.MatchResult;
import de.cweyermann.sspes.api.SspesAPI;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class Spielen implements RequestHandler {

    public static final String ERGEBNIS_PATTERN = "Du hast gegen {0} gespielt. Du wähltest {1}, {0} {2}. Du hast {3}";

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("Spielen"));
    }

    @Override
    public Optional<Response> handle(HandlerInput in) {
        IntentRequest intent = (IntentRequest) in.getRequestEnvelope().getRequest();
        Map<String, Slot> slots = intent.getIntent().getSlots();

        String option = slots.get("option").getValue();

        SspesAPI api = new SspesAPI();
        MatchResult matchResult = api.playUnrankedAgainstRandom(option);

        String speechResult = MessageFormat.format(ERGEBNIS_PATTERN,
                matchResult.getOpponentName(),
                option,
                matchResult.getOpponentChoice(),
                matchResult.getResult().getAussprache());

        return in
                .getResponseBuilder()
                .withSpeech(speechResult)
                .withShouldEndSession(true)
                .build();
    }
}
