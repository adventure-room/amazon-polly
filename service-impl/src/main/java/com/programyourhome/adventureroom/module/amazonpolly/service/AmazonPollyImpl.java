package com.programyourhome.adventureroom.module.amazonpolly.service;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.TextType;

public class AmazonPollyImpl implements AmazonPolly {

    private com.amazonaws.services.polly.AmazonPolly pollyClient;

    @Override
    public void connect(String region) {
        this.pollyClient = AmazonPollyClient.builder()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withClientConfiguration(new ClientConfiguration())
                .withRegion(Regions.valueOf(region))
                .build();
    }

    @Override
    public AudioInputStream synthesizeText(String voiceId, String text) {
        return this.synthesizeResult(voiceId, TextType.Text, text);
    }

    @Override
    public AudioInputStream synthesizeSsml(String voiceId, String ssml) {
        return this.synthesizeResult(voiceId, TextType.Ssml, ssml);
    }

    private AudioInputStream synthesizeResult(String voiceId, TextType textType, String input) {
        SynthesizeSpeechResult result = this.synthesize(voiceId, textType, input, OutputFormat.Pcm);
        return new AudioInputStream(result.getAudioStream(), POLLY_AUDIO_FORMAT, AudioSystem.NOT_SPECIFIED);
    }

    public SynthesizeSpeechResult synthesize(String voiceId, TextType textType, String input, OutputFormat outputFormat) {
        SynthesizeSpeechRequest request = new SynthesizeSpeechRequest()
                .withText(input)
                .withTextType(textType)
                .withVoiceId(voiceId)
                .withOutputFormat(outputFormat)
                .withSampleRate("" + POLLY_SAMPLE_RATE);
        return this.pollyClient.synthesizeSpeech(request);
    }

}