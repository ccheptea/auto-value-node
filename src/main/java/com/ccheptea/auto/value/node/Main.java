package com.ccheptea.auto.value.node;

import com.ccheptea.auto.value.node.models.Campaign;

/**
 * Created by constantin.cheptea
 * on 03/03/2017.
 */
public class Main {
    public static void main(String[] args) {
        Campaign campaign = new Campaign();

//        System.out.println(campaign.node().specifics().keywords().included().value());

        campaign.node().specifics().keywords()
//                .ifNotPresent(() -> System.out.println("Object is null"))
//                .otherwise(System.out::println);
                .ifPresent(System.out::println)
                .otherwise(() -> System.out.println("Object is null"));
    }
}
