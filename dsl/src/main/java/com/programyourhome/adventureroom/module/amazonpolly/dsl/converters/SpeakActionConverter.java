package com.programyourhome.adventureroom.module.amazonpolly.dsl.converters;

import com.programyourhome.adventureroom.dsl.regex.MatchResult;
import com.programyourhome.adventureroom.dsl.regex.RegexActionConverter;
import com.programyourhome.adventureroom.model.Adventure;
import com.programyourhome.adventureroom.module.amazonpolly.model.SpeakAction;

public class SpeakActionConverter implements RegexActionConverter<SpeakAction> {

    @Override
    public String getRegexLine() {
        return CHARACTER_ID + " says " + TEXT;
    }

    @Override
    public SpeakAction convert(MatchResult matchResult, Adventure adventure) {
        SpeakAction action = new SpeakAction();
        action.character = adventure.getCharacter(matchResult.getValue(CHARACTER_ID));
        action.text = matchResult.getValue(TEXT);
        return action;
    }

}
