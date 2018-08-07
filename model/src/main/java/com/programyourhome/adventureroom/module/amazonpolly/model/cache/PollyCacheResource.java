package com.programyourhome.adventureroom.module.amazonpolly.model.cache;

import com.programyourhome.adventureroom.model.toolbox.CacheResource;

public class PollyCacheResource extends CacheResource {

    public static final int NAME_MAX_LENGTH = 30;

    private String voiceId;
    private String speechInput;
    private String speechChecksum;

    @SuppressWarnings("unused")
    private PollyCacheResource() {
    }

    public PollyCacheResource(String voiceId, String speechInput) {
        this.voiceId = voiceId;
        this.speechInput = speechInput;
        this.speechChecksum = this.checksum(this.speechInput);

        if (this.speechInput.length() > NAME_MAX_LENGTH) {
            this.name = this.speechInput.substring(0, NAME_MAX_LENGTH);
        } else {
            this.name = this.speechInput;
        }
        this.extension = "pcm";
        this.mimeType = "audio/pcm";
    }

    @Override
    public String getId() {
        return this.voiceId + "-" + this.speechChecksum;
    }

}
