package utils;

public class Tuple<T,S> {

    private T t;
    private S s;

    public Tuple(T t, S s) {
        this.t = t;
        this.s = s;
    }

    public T fst() {
        return t;
    }

    public S snd() {
        return s;
    }

}
