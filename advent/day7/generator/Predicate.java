package advent.day7.generator;

@FunctionalInterface
public interface Predicate<T> {
    boolean pred(T t);
}
