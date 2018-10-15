package com.programyourhome.adventureroom.module.amazonpolly.executor;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import com.programyourhome.adventureroom.model.execution.ExecutionContext;
import com.programyourhome.adventureroom.model.util.IOUtil;
import com.programyourhome.adventureroom.module.amazonpolly.model.SpeakAction;

public class SpeakActionExecutor extends AbstractAmazonPollyExecutor<SpeakAction> {

    private Clip clip;

    @Override
    public void execute(SpeakAction action, ExecutionContext context) {
        AudioInputStream audioInputStream = this.getAmazonPolly(context).synthesizeText(action.character.voiceId, action.text);
        try {
            this.clip = AudioSystem.getClip();
            synchronized (this.clip) {
                this.clip.open(audioInputStream);
                this.clip.start();
            }
            IOUtil.waitForCondition(() -> !this.clip.isActive());
        } catch (IOException | LineUnavailableException e) {
            throw new IllegalStateException("Exception occured during playback", e);
        }
    }

    @Override
    public void stop(ExecutionContext context) {
        IOUtil.waitForCondition(() -> this.clip != null && this.clip.isActive());
        synchronized (this.clip) {
            this.clip.stop();
        }
    }

}
