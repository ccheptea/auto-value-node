package input;

import com.ccheptea.auto.value.node.runtime.Node;
import com.ccheptea.auto.value.node.runtime.Node_Wrapper;
import java.lang.String;

public final class Node_School extends Node<School> {

    public Node_School(School value) {
        super(value);
    }

    public final Node_Wrapper<String> name() {
        return new Node_Wrapper<>(value == null ? null : value.name());
    }

    public final Node_Wrapper<String> country() {
        return new Node_Wrapper<>(value == null ? null : value.country());
    }
}