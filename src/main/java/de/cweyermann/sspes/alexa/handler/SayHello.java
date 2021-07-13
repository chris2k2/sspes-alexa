package de.cweyermann.sspes.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.requestType;

public class SayHello implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        return
                input
                        .getResponseBuilder()
                        .withSpeech("Hallo, willkommen bei Schere Stein Papier Echse Spock! Sage \"ich möchte spielen\" um ein Spiel zu starten")
                        .withShouldEndSession(false)
                        .build();
    }
}
