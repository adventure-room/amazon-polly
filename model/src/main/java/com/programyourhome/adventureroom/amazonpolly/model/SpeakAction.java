package com.programyourhome.adventureroom.amazonpolly.model;

import com.programyourhome.adventureroom.amazonpolly.model.characters.PollyCharacter;
import com.programyourhome.adventureroom.model.script.action.Action;

public class SpeakAction implements Action {

    public PollyCharacter character;

    public String text;

}
