package input;

import com.google.auto.value.AutoValue;
import input.AutoValue_School;

/**
 * Created by constantin.cheptea
 * on 09/03/2017.
 */
@AutoValue
public abstract class School {

    public abstract String name();

    public abstract String country();

    public abstract Node_School node();

    public static Builder builder() {
        return new AutoValue_School.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder name(String name);

        public abstract Builder country(String country);

        public abstract School build();
    }
}
