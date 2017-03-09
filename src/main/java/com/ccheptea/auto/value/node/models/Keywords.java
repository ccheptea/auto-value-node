package com.ccheptea.auto.value.node.models;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by constantin.cheptea
 * on 03/03/2017.
 */
public class Keywords {
    public List<String> included = new ArrayList<>();
    public List<String> excluded = new ArrayList<>();
    public String group = null;

    public Keywords(){
        included.add("fish");
        included.add("frog");

        excluded.add("bird");
        excluded.add("insect");
    }

    public $.Node_Keywords node() {
        return new $.Node_Keywords(this);
    }

    @Override
    public String toString() {
        return "Keywords{" +
                "group='" + group + '\'' +
                '}';
    }
}
