package io.ghostbuster91.retrofit.adapter.vavr;

import io.vavr.control.Try;
import retrofit2.Call;
import retrofit2.CallAdapter;

import java.lang.reflect.Type;

public class BodyCallAdapter<R> implements CallAdapter<R, Try<R>> {
    private final Type innerType;

    public BodyCallAdapter(Type innerType) {
        this.innerType = innerType;
    }

    @Override
    public Type responseType() {
        return innerType;
    }

    @Override
    public Try<R> adapt(Call<R> call) {
        return Try.of(() -> call.execute().body());
    }
}
