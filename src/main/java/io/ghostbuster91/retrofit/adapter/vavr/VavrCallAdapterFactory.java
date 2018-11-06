package io.ghostbuster91.retrofit.adapter.vavr;

import io.vavr.control.Try;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class VavrCallAdapterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != Try.class) {
            return null;
        }

        Type innerType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> outerType = getRawType(innerType);
        if (outerType == Response.class) {
            if (!(innerType instanceof ParameterizedType)) {
                throw new IllegalStateException("Response must be parameterized"
                        + " as Response<Foo> or Response<? extends Foo>");
            }
            Type responseType = getParameterUpperBound(0, (ParameterizedType) innerType);
            return new ResponseCallAdapter(responseType);
        } else {
            return new BodyCallAdapter(innerType);
        }
    }
}
