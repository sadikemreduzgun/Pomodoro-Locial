package com.sadikemreduzgun.actions;
import com.sadikemreduzgun.config.TxtFileReader;

import java.util.Random;

// random username creator for users, username creations are both free and random suggestions
public class RandomUnameCreator {
    // initialize txt reader
    TxtFileReader txtFileReader = new TxtFileReader();

    public String createRandomUname(){
        // get random username into array of strings
        String[] randomUsernames = txtFileReader.readFile("sources/random_username_words.txt").split(",");
        String[] randomVerbs = txtFileReader.readFile("sources/random_username_verbs.txt").split(",");

        Random random = new Random();
        // get random verb + username
        int randomSelectedUname = random.nextInt(randomUsernames.length-1);
        int randomSelectedVerb = random.nextInt(randomVerbs.length-1);

        // System.out.println(randomVerbs[randomSelectedVerb]);
        // System.out.println(randomUsernames[randomSelectedUname]);

        return randomVerbs[randomSelectedVerb] + " " + randomUsernames[randomSelectedUname];

    }

}
