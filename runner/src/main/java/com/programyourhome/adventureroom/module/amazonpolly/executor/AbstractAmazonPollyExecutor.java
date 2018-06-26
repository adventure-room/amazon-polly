package com.programyourhome.adventureroom.module.amazonpolly.executor;

import com.programyourhome.adventureroom.amazonpolly.module.AmazonPollyAdventureModule;
import com.programyourhome.adventureroom.amazonpolly.service.AmazonPolly;
import com.programyourhome.adventureroom.model.script.action.Action;
import com.programyourhome.iotadventure.runner.action.executor.ActionExecutor;
import com.programyourhome.iotadventure.runner.context.ExecutionContext;

public abstract class AbstractAmazonPollyExecutor<A extends Action> implements ActionExecutor<A> {

    protected AmazonPollyAdventureModule getModule(ExecutionContext context) {
        return context.getModule(AmazonPollyAdventureModule.ID);
    }

    protected AmazonPolly getAmazonPolly(ExecutionContext context) {
        return this.getModule(context).getAmazonPolly();
    }

}
