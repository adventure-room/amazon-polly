package com.programyourhome.adventureroom.module.amazonpolly.model;

import com.programyourhome.adventureroom.model.script.action.Action;
import com.programyourhome.adventureroom.module.amazonpolly.model.characters.PollyCharacter;

public class SpeakAction implements Action {

    public PollyCharacter character;

    public String text;

}
