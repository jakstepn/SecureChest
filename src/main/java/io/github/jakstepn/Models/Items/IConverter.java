package io.github.jakstepn.Models.Items;

public interface IConverter<T> {
    T convert(String value);
}
