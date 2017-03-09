package com.ccheptea.auto.value.node.models;

import com.ccheptea.auto.value.node.Node;
import com.ccheptea.auto.value.node.Node_Wrapper;

import java.util.List;

/**
 * Created by constantin.cheptea
 * on 05/03/2017.
 */
public class $ {

    public static class Nodeable_Campaign extends Node<Campaign> {
        public Nodeable_Campaign(Campaign campaign) {
            super(campaign);
        }

        public Node_Specifics specifics() {
            return new Node_Specifics(value == null ? null : this.value.specifics);
        }
    }

    public static class Node_Specifics extends Node<Specifics> {

        public Node_Specifics(Specifics specifics) {
            super(specifics);
        }

        public Node_Keywords keywords() {
            return new Node_Keywords(value == null ? null : value.keywords);
        }

        public Node_Wrapper<String> name() {
            return new Node_Wrapper<>(value == null ? null : value.name);
        }
    }

    public static class Node_Keywords extends Node<Keywords> {

        public Node_Keywords(Keywords keywords) {
            super(keywords);
        }

        public Node_Wrapper<String> group() {
            return new Node_Wrapper<>(value == null ? null : value.group);
        }

        public Node_Wrapper<List<String>> included() {
            return new Node_Wrapper<>(value == null ? null : value.included);
        }

        public Node_Wrapper<List<String>> excluded() {
            return new Node_Wrapper<>(value == null ? null : value.excluded);
        }
    }

//    public static class Node_Wrapper<T> {
//        private T value;
//
//        public Node_Wrapper(T value) {
//            this.value = value;
//        }
//
//        public T value() {
//            return value;
//        }
//
//        public boolean exists() {
//            return value != null;
//        }
//    }
}
