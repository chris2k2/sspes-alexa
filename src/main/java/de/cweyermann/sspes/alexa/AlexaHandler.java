package de.cweyermann.sspes.alexa;


import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import de.cweyermann.sspes.alexa.handler.Hilfe;
import de.cweyermann.sspes.alexa.handler.SayHello;
import de.cweyermann.sspes.alexa.handler.Spielen;
import de.cweyermann.sspes.alexa.handler.Statistik;

public class AlexaHandler extends SkillStreamHandler {

    public AlexaHandler() {
        super(Skills
                .standard()
                .addRequestHandlers(
                        new SayHello(),
                        new Spielen(),
                        new Statistik(),
                        new Hilfe()
                )
                .withSkillId("amzn1.ask.skill.be2cf04e-2685-495d-a4e0-9649cf7700f8")
                .withAutoCreateTable(true)
                .withTableName("chris-alexa")
                .build());
    }

}
