package input;

import java.lang.Override;
import java.lang.String;

final class AutoValue_Wand extends $AutoValue_Wand {
    AutoValue_Wand(Owner owner, String wood, int status, boolean broken) {
        super(owner, wood, status, broken);
    }

    @Override
    public final Node_Wand node() {
        return new Node_Wand(this);
    }
}