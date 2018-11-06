package io.ghostbuster91.retrofit.adapter.vavr;

import io.vavr.control.Try;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;

import java.lang.reflect.Type;

public class ResponseCallAdapter<R> implements CallAdapter<R, Try<Response<R>>> {
    private final Type innerType;

    public ResponseCallAdapter(Type innerType) {
        this.innerType = innerType;
    }

    @Override
    public Type responseType() {
        return innerType;
    }

    @Override
    public Try<Response<R>> adapt(Call<R> call) {
        return Try.of(call::execute);
    }
}


