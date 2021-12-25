package ru.vlsv.simplenotes.repositories;

public interface Callback<T> {

    void onSuccess(T result);

    void onError(Throwable error);

}
