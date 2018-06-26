package com.programyourhome.adventureroom.amazonpolly.executor;

import com.programyourhome.adventureroom.amazonpolly.model.SpeakAction;
import com.programyourhome.iotadventure.runner.context.ExecutionContext;

public class SpeakActionExecutor extends AbstractAmazonPollyExecutor<SpeakAction> {

    @Override
    public void execute(SpeakAction action, ExecutionContext context) {
        this.getAmazonPolly(context).sayText(action.character.voiceId, action.text);
    }

}
