package input;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Wand {
    public abstract Owner owner();

    public abstract String wood();

    public abstract Node_Wand node();
}