package advent.day7.generator;

import java.util.List;

@FunctionalInterface
public interface Function<T, U> {
    U apply(List<T> list);
}
