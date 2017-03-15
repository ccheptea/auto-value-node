package input;

import java.lang.Override;
import java.lang.String;

final class AutoValue_School extends $AutoValue_School {
    AutoValue_Wand(String name, String country) {
        super(name, country);
    }

    @Override
    public final Node_School node() {
        return new Node_School(this);
    }
}