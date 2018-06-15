package com.programyourhome.adventureroom.amazonpolly.service;

public interface AmazonPolly {

    public void sayText(String voiceId, String text);

    public void saySsml(String voiceId, String ssml);

}