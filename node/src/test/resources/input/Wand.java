package input;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Wand {
    public abstract Owner owner();

    public abstract String wood();

    public abstract int status();


    public abstract boolean broken();

    public abstract Node_Wand node();
}