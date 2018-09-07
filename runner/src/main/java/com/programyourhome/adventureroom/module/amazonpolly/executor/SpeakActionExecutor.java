package com.programyourhome.adventureroom.module.amazonpolly.executor;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import com.programyourhome.adventureroom.model.execution.ExecutionContext;
import com.programyourhome.adventureroom.module.amazonpolly.model.SpeakAction;

public class SpeakActionExecutor extends AbstractAmazonPollyExecutor<SpeakAction> {

    @Override
    public void execute(SpeakAction action, ExecutionContext context) {
        AudioInputStream audioInputStream = this.getAmazonPolly(context).synthesizeText(action.character.voiceId, action.text);
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (IOException | LineUnavailableException e) {
            throw new IllegalStateException("Exception occured during playback", e);
        }
    }

}
