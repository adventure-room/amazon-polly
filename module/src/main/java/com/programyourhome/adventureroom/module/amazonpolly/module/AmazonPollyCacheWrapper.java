package com.programyourhome.adventureroom.module.amazonpolly.module;

import java.util.function.BiFunction;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.programyourhome.adventureroom.model.toolbox.CacheService;
import com.programyourhome.adventureroom.model.toolbox.DataStream;
import com.programyourhome.adventureroom.module.amazonpolly.model.cache.PollyCacheResource;
import com.programyourhome.adventureroom.module.amazonpolly.service.AmazonPolly;

public class AmazonPollyCacheWrapper implements AmazonPolly {

    private final AmazonPolly wrappedPolly;
    private final CacheService cacheService;

    public AmazonPollyCacheWrapper(AmazonPolly wrappedPolly, CacheService cacheService) {
        this.wrappedPolly = wrappedPolly;
        this.cacheService = cacheService;
    }

    @Override
    public void connect(String region) {
        this.wrappedPolly.connect(region);
    }

    @Override
    public AudioInputStream synthesizeText(String voiceId, String text) {
        return this.cacheOrWrapper(this.wrappedPolly::synthesizeText, voiceId, text);
    }

    @Override
    public AudioInputStream synthesizeSsml(String voiceId, String ssml) {
        return this.cacheOrWrapper(this.wrappedPolly::synthesizeSsml, voiceId, ssml);
    }

    private AudioInputStream cacheOrWrapper(BiFunction<String, String, AudioInputStream> wrapperFunction, String voiceId, String speechInput) {
        PollyCacheResource resource = new PollyCacheResource(voiceId, speechInput);
        if (!this.cacheService.hasResource(resource.getId())) {
            System.out.println("Resource not found in cache, invoking actual Polly service");
            AudioInputStream audioInputStream = wrapperFunction.apply(voiceId, speechInput);
            // When we store the resource using the original input stream, it will be fully read and we cannot re-use it.
            // But since we just stored it, we can just return the cached resource input stream, same as if it were in the cache already.
            this.cacheService.storeResource(resource, new DataStream(audioInputStream, "audio/pcm"));
        } else {
            // TODO: remove debugging
            System.out.println("Resource found in cache! Using cached Polly result.");
        }
        // TODO: Polly stream wrapping util method
        return new AudioInputStream(this.cacheService.getCacheDataStream(resource.getId()).getInputStream(),
                AmazonPolly.POLLY_AUDIO_FORMAT, AudioSystem.NOT_SPECIFIED);
    }

}
