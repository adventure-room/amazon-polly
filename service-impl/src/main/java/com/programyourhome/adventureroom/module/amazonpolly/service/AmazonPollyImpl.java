package com.programyourhome.adventureroom.module.amazonpolly.service;

import static com.programyourhome.adventureroom.module.amazonpolly.service.PollyAudioFormat.POLLY_SAMPLE_RATE;
import static com.programyourhome.adventureroom.module.amazonpolly.service.PollyAudioFormat.getPollyAudioFormat;
import static javazoom.jl.player.FactoryRegistry.systemRegistry;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.TextType;
import com.programyourhome.adventureroom.amazonpolly.service.AmazonPolly;
import com.programyourhome.adventureroom.amazonpolly.service.model.PollyResult;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class AmazonPollyImpl implements AmazonPolly {

    private com.amazonaws.services.polly.AmazonPolly pollyClient;

    public AmazonPollyImpl() {
    }

    @Override
    public void connect(String region) {
        this.pollyClient = AmazonPollyClient.builder()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withClientConfiguration(new ClientConfiguration())
                .withRegion(Regions.valueOf(region))
                .build();
    }

    @Override
    public PollyResult synthesizeText(String voiceId, String text) {
        return this.synthesizeResult(voiceId, TextType.Text, text);
    }

    @Override
    public PollyResult synthesizeSsml(String voiceId, String ssml) {
        return this.synthesizeResult(voiceId, TextType.Ssml, ssml);
    }

    private PollyResult synthesizeResult(String voiceId, TextType textType, String input) {
        PollyResult result = new PollyResult();
        result.inputStream = this.synthesize(voiceId, textType, input, OutputFormat.Pcm).getAudioStream();
        result.audioFormat = getPollyAudioFormat(POLLY_SAMPLE_RATE);
        return result;
    }

    @Override
    public void sayText(String voiceId, String text) {
        this.play(this.synthesize(voiceId, TextType.Text, text, OutputFormat.Mp3));
    }

    @Override
    public void saySsml(String voiceId, String ssml) {
        this.play(this.synthesize(voiceId, TextType.Ssml, ssml, OutputFormat.Mp3));
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

    private void play(SynthesizeSpeechResult result) {
        try {
            new AdvancedPlayer(result.getAudioStream(), systemRegistry().createAudioDevice()).play();
        } catch (JavaLayerException e) {
            throw new IllegalStateException("Exception occured during playback", e);
        }

    }

}