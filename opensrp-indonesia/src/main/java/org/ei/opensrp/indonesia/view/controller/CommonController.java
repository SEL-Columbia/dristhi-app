package org.ei.opensrp.indonesia.view.controller;

import org.ei.opensrp.view.contract.SmartRegisterClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Dimas Ciputra on 5/20/15.
 */
public class CommonController {

    public CharSequence[] onRandomNameChars(final SmartRegisterClient client,
                                            final List<SmartRegisterClient> smartRegisterClients,
                                            List<String> randomDummyName,
                                            int optionSize) {

        List<String> randomName = new ArrayList<String>();

        for(SmartRegisterClient smartRegisterClient : smartRegisterClients) {
            randomName.add(smartRegisterClient.name());
        }

        Collections.shuffle(randomName);

        if(randomName.size() < optionSize) {
            randomName.addAll(randomDummyName.subList(0, optionSize - randomName.size()));
            return randomName.toArray(new CharSequence[randomName.size()]);
        }

        randomName = randomName.subList(0, optionSize);

        if(!randomName.contains(client.name())) {
            randomName = randomName.subList(0, optionSize-1);
            Random random = new Random();
            randomName.add(random.nextInt(optionSize),client.name());
        }

        return randomName.toArray(new CharSequence[randomName.size()]);
    }
}
