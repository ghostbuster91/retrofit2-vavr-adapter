package io.ghostbuster91.retrofit.adapter.vavr;

import com.google.gson.Gson;
import io.vavr.control.Try;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class VavrCallAdapterFactoryTest {

    @Rule
    public final MockWebServer server = new MockWebServer();
    private Api api;

    public interface Api {
        @GET("/")
        Try<Response<DummyObject>> getResponse();

        @GET("/")
        Try<DummyObject> getBody();
    }

    @Before
    public void setUp() throws Exception {
        server.enqueue(new MockResponse().setBody("{\"userId\":\"123\"}"));
        final Retrofit build = new Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(new VavrCallAdapterFactory())
                .build();
        api = build.create(Api.class);
    }

    @Test
    public void shouldReturnRespone() {
        Try<Response<DummyObject>> myResps = api.getResponse();
        Assert.assertEquals(new DummyObject("123"), myResps.get().body());
    }

    @Test
    public void shouldReturnBody() {
        Try<DummyObject> myResp = api.getBody();
        Assert.assertEquals(new DummyObject("123"), myResp.get());
    }
}
