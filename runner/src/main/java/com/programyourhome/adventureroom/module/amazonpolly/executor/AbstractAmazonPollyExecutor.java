package com.programyourhome.adventureroom.module.amazonpolly.executor;

import com.programyourhome.adventureroom.model.execution.ExecutionContext;
import com.programyourhome.adventureroom.model.script.action.Action;
import com.programyourhome.adventureroom.module.amazonpolly.module.AmazonPollyAdventureModule;
import com.programyourhome.adventureroom.module.amazonpolly.service.AmazonPolly;
import com.programyourhome.iotadventure.runner.action.executor.ActionExecutor;

public abstract class AbstractAmazonPollyExecutor<A extends Action> implements ActionExecutor<A> {

    protected AmazonPollyAdventureModule getModule(ExecutionContext context) {
        return context.getModule(AmazonPollyAdventureModule.ID);
    }

    protected AmazonPolly getAmazonPolly(ExecutionContext context) {
        return this.getModule(context).getAmazonPolly();
    }

}
