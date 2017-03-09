package input;

import com.ccheptea.auto.value.node.Node;
import com.ccheptea.auto.value.node.Node_Wrapper;

import java.lang.String;

public final class Node_Owner extends Node<Owner> {

    public Node_Owner(Owner value) {
        super(value);
    }

    public final Node_Wrapper<String> firstName() {
        return new Node_Wrapper<>(value == null ? null : value.firstName());
    }

    public final Node_Wrapper<String> lastName() {
        return new Node_Wrapper<>(value == null ? null : value.lastName());
    }

    public final Node_School school() {
        return new Node_School(value == null ? null : value.school());
    }
}