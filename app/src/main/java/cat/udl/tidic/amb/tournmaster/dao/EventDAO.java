package cat.udl.tidic.amb.tournmaster.dao;

import java.util.Map;

import cat.udl.tidic.amb.tournmaster.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

public interface EventDAO {

    //@Headers("Authorization:656e50e154865a5dc469b80437ed2f963b8f58c8857b66c9bf")
    @GET("/account/profile")
    Call<User> getUser(@HeaderMap Map<String, String> headers);

}
