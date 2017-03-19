package input;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Wand {
    public abstract Owner owner();

    abstract String wood();

    protected abstract int status();


    public abstract boolean broken();

    public abstract Node_Wand node();
}