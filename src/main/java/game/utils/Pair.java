package game.utils;

public class Pair<T1, T2> {
    /**
     * The first element in the pair of type T1
     */
    public final T1 first;
    /**
     * The second element in the pair of type T2
     */
    public final T2 second;

    /**
     * The template constructor
     * T1 is type 1
     * T2 is type 2
     *
     * @param first  (T1) The first element in the pair
     * @param second (T2) The second element in the pair
     */
    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }
}
