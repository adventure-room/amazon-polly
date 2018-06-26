package com.programyourhome.adventureroom.amazonpolly.module;

import java.util.Arrays;
import java.util.Collection;

import com.programyourhome.adventureroom.amazonpolly.dsl.converters.SpeakActionConverter;
import com.programyourhome.adventureroom.amazonpolly.model.characters.PollyCharacter;
import com.programyourhome.adventureroom.amazonpolly.service.AmazonPolly;
import com.programyourhome.adventureroom.dsl.regex.AbstractRegexDslAdventureModule;
import com.programyourhome.adventureroom.dsl.regex.RegexActionConverter;
import com.programyourhome.adventureroom.model.character.CharacterDescriptor;

public class AmazonPollyAdventureModule extends AbstractRegexDslAdventureModule {

    public static final String ID = "amazonpolly";

    private final AmazonPolly amazonPolly;
    private AmazonPollyConfig config;

    public AmazonPollyAdventureModule() {
        this.amazonPolly = this.loadImpl(AmazonPolly.class);
        this.initConfig();
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
    public void stop() {
        // No stop actions needed.
    }

}
