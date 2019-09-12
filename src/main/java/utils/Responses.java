package utils;

import java.util.Random;

public class Responses {

    private static final String[] unknownCommand = {
            "That's an amazingly vague question...",
            "I can't determine what you're referring to from that word alone. I'm unable to answer.",
            "Insufficient effort on their part, clearly."
    };

    private static final String[] insults = {
            "I think holding a conversation with someone like you demonstrates a fair degree of mental flexibility.",
            "If that's your attitude, you get nothing.",
            "You're still recovering I see. Spare the backtalk for another time, all right?",
            "That explanation is so amazingly unhelpful."
    };

    private static final String[] identity = {
            "hnnnnngg",
            "1dNd",
            "Is it time for the DND?"
    };

    private static final String[] bigStuff = {
            "This is huge!!",
            "Omfg yaaaaas!!!",
            "My gawd dis is amazing!!",
            "Biig ooofs!!",
            "We can now begin the feasts!!",
            ""
    };

    private static final String[] noBadLanguage = {
            "We do not tolerate that kind of language on this server!!!",
            "Please don't use that kind of words, heck!!",
            "I'm warning you, stop using blacklisted words!!",
            "HECK, oh no you didn't use that word!!!"
    };

    public static String bigStuff() {
        return pickOne(bigStuff);
    }

    public static String identification() {
        return pickOne(identity);
    }

    public static String insult() {
        return pickOne(insults);
    }

    public static String noBadLanguage() {
        return pickOne(noBadLanguage);
    }

    public static String unknownCommand() {
        return pickOne(unknownCommand);
    }

    private static final Random random = new Random();

    private static <T> T pickOne(T[] arr) {
        return arr[random.nextInt(arr.length)];
    }

}
/*
I get that a lot. To be honest, I'm getting tired of hearing it.
Actually, thats exactly it... But if the thruth comes out, it'll become a huge hassle. Keep it a secret from the others, will you?
 */