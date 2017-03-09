package input;

import com.ccheptea.auto.value.node.Node;
import com.ccheptea.auto.value.node.Node_Wrapper;
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
}