package input;

import com.google.auto.value.AutoValue;

/**
 * Created by constantin.cheptea
 * on 09/03/2017.
 */
@AutoValue
public abstract class Owner implements IOwner{
    public abstract Node_Owner node();
}
