package de.cweyermann.sspes.alexa;


import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import de.cweyermann.sspes.alexa.handler.SayHello;
import de.cweyermann.sspes.alexa.handler.Spielen;

public class Handler extends SkillStreamHandler {

    private static Skill getSkill() {

        return Skills.standard()
                .addRequestHandlers(
                        new SayHello(),
                        new Spielen()
                )
                .withSkillId("amzn1.ask.skill.be2cf04e-2685-495d-a4e0-9649cf7700f8")
                .build();
    }

    public Handler() {
        super(getSkill());
    }

}
