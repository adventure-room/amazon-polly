package com.programyourhome.adventureroom.amazonpolly.module;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.regex.Pattern;

import com.programyourhome.adventureroom.amazonpolly.dsl.converters.SpeakActionConverter;
import com.programyourhome.adventureroom.amazonpolly.model.characters.PollyCharacter;
import com.programyourhome.adventureroom.amazonpolly.service.AmazonPolly;
import com.programyourhome.adventureroom.dsl.regex.AbstractRegexDslAdventureModule;
import com.programyourhome.adventureroom.dsl.regex.RegexActionConverter;
import com.programyourhome.adventureroom.model.character.CharacterDescriptor;

public class AmazonPollyAdventureModule extends AbstractRegexDslAdventureModule {

    public static final String ID = "amazonpolly";

    private AmazonPolly amazonPolly;
    private AmazonPollyConfig config;

    public AmazonPollyAdventureModule() {
        // TODO: move to util
        // We assume there will be one implementation available on the classpath. If not, behavior is undefined.
        ServiceLoader.load(AmazonPolly.class).forEach(impl -> this.amazonPolly = impl);
        this.initConfig();
    }

    private void initConfig() {
        this.config = new AmazonPollyConfig();
        this.config.id = ID;
        this.config.name = "Amazon Polly";
        this.config.description = "Module to use the Amazon Polly service";

        CharacterDescriptor characterDescriptor = new CharacterDescriptor();
        characterDescriptor.id = "polly";
        characterDescriptor.name = "Amazon Polly based voice characters";
        characterDescriptor.clazz = PollyCharacter.class;
        this.config.characterDescriptors.put(characterDescriptor.getId(), characterDescriptor);
    }

    public AmazonPolly getAmazonPolly() {
        return this.amazonPolly;
    }

    @Override
    public AmazonPollyConfig getConfig() {
        return this.config;
    }

    @Override
    protected Map<Pattern, RegexActionConverter<?>> getRegexActionConverters() {
        Map<Pattern, RegexActionConverter<?>> converters = new HashMap<>();
        Pattern pattern = Pattern.compile("(?<id>[A-Za-z0-9]+) says \"(?<text>[^\"]+)\"");
        converters.put(pattern, new SpeakActionConverter());

        return converters;
    }

}
