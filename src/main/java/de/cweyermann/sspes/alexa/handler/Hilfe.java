package de.cweyermann.sspes.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class Hilfe implements RequestHandler {



    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.HelpIntent"));
    }


    @Override
    public Optional<Response> handle(HandlerInput input) {
                return input
                .getResponseBuilder()
                .withSpeech("Ich kann das Spiel Schere, Stein, Papier, Echse, spock mit dir spielen. Sage zum Spielen einfach 'ich möchte spielen' um deine bisherigen " +
                        "Ergebnisse zu erfahren frage mich 'wie oft habe ich gewonnen?'. Was möchtest du machen?" )
                .withShouldEndSession(false)
                .build();
    }

}
