package com.programyourhome.adventureroom.amazonpolly.service;

import com.programyourhome.adventureroom.amazonpolly.service.model.PollyResult;

public interface AmazonPolly {

    public void connect(String region);

    public PollyResult synthesizeText(String voiceId, String text);

    public PollyResult synthesizeSsml(String voiceId, String ssml);

    public void sayText(String voiceId, String text);

    public void saySsml(String voiceId, String ssml);

}