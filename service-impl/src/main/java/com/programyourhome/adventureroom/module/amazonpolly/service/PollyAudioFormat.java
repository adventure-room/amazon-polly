package com.programyourhome.adventureroom.amazonpolly.service;

import javax.sound.sampled.AudioFormat;

public class PollyAudioFormat {

    // TODO: experiment with these values, signed & big endian seems not not to make a difference, why?
    public static final int POLLY_SAMPLE_RATE = 16000;
    public static final int POLLY_SAMPLE_SIZE = 16;
    public static final int POLLY_CHANNELS = 1;
    public static final boolean POLLY_SIGNED = true;
    public static final boolean POLLY_BIG_ENDIAN = false;

    private PollyAudioFormat() {
    }

    public static AudioFormat getPollyAudioFormat(int sampleRate) {
        return new AudioFormat(sampleRate, POLLY_SAMPLE_SIZE, POLLY_CHANNELS, POLLY_SIGNED, POLLY_BIG_ENDIAN);
    }

}
