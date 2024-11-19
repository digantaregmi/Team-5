package com.example.moodymovie.constants;

public enum OutputMoods {
    GRINNING_FACE("GrinningFace"),                 // 😀 Represents happiness, joy, or friendliness
    FACE_WITH_TEARS_OF_JOY("FaceWithTearsOfJoy"),  // 😂 Used to express laughter to the point of tears
    SMILING_FACE_WITH_SUNGLASSES("SmilingFaceWithSunglasses"), // 😎 Represents coolness, confidence, or enjoying a sunny day
    CRYING_FACE("CryingFace"),                     // 😢 Indicates sadness, sorrow, or disappointment
    EXPLODING_HEAD("ExplodingHead"),               // 🤯 Represents shock, amazement, or mind-blowing information
    THINKING_FACE("ThinkingFace"),                 // 🤔 Often used to show pondering, thinking, or considering something
    SEE_NO_EVIL_MONKEY("SeeNoEvilMonkey"),         // 🙈 Expresses playfulness, shyness, or sometimes embarrassment
    HEART_EYES("HeartEyes");                       // 😍 Represents love, adoration, or attraction toward something or someone

    private final String displayName;

    OutputMoods(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
