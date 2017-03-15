package input;

import java.lang.Override;
import java.lang.String;

final class AutoValue_Owner extends $AutoValue_Owner {
    AutoValue_Wand(String firstName, String lastName, School school) {
        super(firstName, lastName, school);
    }

    @Override
    public final Node_Owner node() {
        return new Node_Owner(this);
    }
}