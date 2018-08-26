package com.programyourhome.adventureroom.module.amazonpolly.module;

import java.util.Arrays;
import java.util.Collection;

import com.programyourhome.adventureroom.dsl.regex.AbstractRegexDslAdventureModule;
import com.programyourhome.adventureroom.dsl.regex.RegexActionConverter;
import com.programyourhome.adventureroom.model.Adventure;
import com.programyourhome.adventureroom.model.character.CharacterDescriptor;
import com.programyourhome.adventureroom.model.toolbox.Toolbox;
import com.programyourhome.adventureroom.module.amazonpolly.dsl.converters.SpeakActionConverter;
import com.programyourhome.adventureroom.module.amazonpolly.model.characters.PollyCharacter;
import com.programyourhome.adventureroom.module.amazonpolly.service.AmazonPolly;

public class AmazonPollyAdventureModule extends AbstractRegexDslAdventureModule {

    public static final String ID = "amazonpolly";

    private AmazonPolly amazonPolly;
    private AmazonPollyConfig config;
    private Toolbox toolbox;

    public AmazonPollyAdventureModule() {
        this.amazonPolly = this.loadImpl(AmazonPolly.class);
        this.initConfig();
    }

    public Toolbox getToolbox() {
        return this.toolbox;
    }

    @Override
    public void setToolbox(Toolbox toolbox) {
        this.toolbox = toolbox;
        // Create a cache wrapper around the Amazon Polly service to prevent synthesizing the same audio over and over again.
        this.amazonPolly = new AmazonPollyCacheWrapper(this.amazonPolly, this.toolbox.getCacheService());
    }

    private void initConfig() {
        this.config = new AmazonPollyConfig();
        this.config.id = ID;
        this.config.name = "Amazon Polly";
        this.config.description = "Module to use the Amazon Polly service";

        CharacterDescriptor<PollyCharacter> characterDescriptor = new CharacterDescriptor<>();
        characterDescriptor.id = "polly";
        characterDescriptor.name = "Amazon Polly based voice characters";
        characterDescriptor.clazz = PollyCharacter.class;
        this.config.addCharacterDescriptor(characterDescriptor);

        this.config.addTask("Connect to Amazon AWS", () -> this.amazonPolly.connect(this.config.region));
    }

    @Override
    public void start(Adventure adventure) {
        // No start actions needed.
    }

    public AmazonPolly getAmazonPolly() {
        return this.amazonPolly;
    }

    @Override
    public AmazonPollyConfig getConfig() {
        return this.config;
    }

    @Override
    protected Collection<RegexActionConverter<?>> getRegexActionConverters() {
        return Arrays.asList(new SpeakActionConverter());
    }

    @Override
    public void stop(Adventure adventure) {
        // No stop actions needed.
    }

}
