package input;

import com.google.auto.value.AutoValue;

/**
 * Created by constantin.cheptea
 * on 09/03/2017.
 */
@AutoValue
public abstract class School {

    public abstract String name();

    public abstract String country();

    public abstract Node_School node();
}
