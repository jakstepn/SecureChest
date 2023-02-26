package io.github.jakstepn.Exceptions;

public class StringIsNotLocationException extends Exception{
    public String value;

    public StringIsNotLocationException(String value) {
        this.value = value;
    }
}
