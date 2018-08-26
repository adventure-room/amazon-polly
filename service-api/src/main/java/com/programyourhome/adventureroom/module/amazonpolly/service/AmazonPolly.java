package com.programyourhome.adventureroom.module.amazonpolly.service;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

public interface AmazonPolly {

    public static final int POLLY_SAMPLE_RATE = 16000;
    public static final int POLLY_SAMPLE_SIZE = 16;
    public static final int POLLY_CHANNELS = 1;
    public static final boolean POLLY_SIGNED = true;
    public static final boolean POLLY_BIG_ENDIAN = false;

    public static final AudioFormat POLLY_AUDIO_FORMAT = new AudioFormat(POLLY_SAMPLE_RATE, POLLY_SAMPLE_SIZE, POLLY_CHANNELS, POLLY_SIGNED, POLLY_BIG_ENDIAN);

    public void connect(String region);

    public AudioInputStream synthesizeText(String voiceId, String text);

    public AudioInputStream synthesizeSsml(String voiceId, String ssml);

}