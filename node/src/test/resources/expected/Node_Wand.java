package input;

import com.ccheptea.auto.value.node.runtime.Node;
import com.ccheptea.auto.value.node.runtime.Node_Wrapper;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;

public final class Node_Wand extends Node<Wand> {

    public Node_Wand(Wand value) {
        super(value);
    }

    public final Node_Owner owner() {
        return new Node_Owner(value == null ? null : value.owner());
    }

    public final Node_Wrapper<String> wood() {
        return new Node_Wrapper<>(value == null ? null : value.wood());
    }

    public final Node_Wrapper<Integer> status() {
        return new Node_Wrapper<>(value == null ? null : value.status());
    }

    public final Node_Wrapper<Boolean> broken() {
        return new Node_Wrapper<>(value == null ? null : value.broken());
    }
}