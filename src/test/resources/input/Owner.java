package input;

import com.google.auto.value.AutoValue;

/**
 * Created by constantin.cheptea
 * on 09/03/2017.
 */
@AutoValue
public abstract class Owner {
    public abstract String firstName();

    public abstract String lastName();

    public abstract School school();

    public abstract Node_Owner node();
}
