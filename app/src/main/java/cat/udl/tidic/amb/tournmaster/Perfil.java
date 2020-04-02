package cat.udl.tidic.amb.tournmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import cat.udl.tidic.amb.tournmaster.preferences.PreferencesProvider;
import cat.udl.tidic.amb.tournmaster.services.UserService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil extends AppCompatActivity {
    private TextView user;
    private TextView mail;
    private TextView rols;
    private TextView sexo;
    private TextView fecha;
    private TextView telf;
    private UserService userService;
    private SharedPreferences mPreferences;
    private Button cerrar;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Intent intent = getIntent();

        user = findViewById(R.id.text_users);
        mail = findViewById(R.id.text_mail);
        rols = findViewById(R.id.rol);
        sexo = findViewById(R.id.text_sexo);
        cerrar = findViewById(R.id.btn_tencarSessio);

        userService = RetrofitClientInstance.
                getRetrofitInstance().create(UserService.class);

        this.mPreferences = PreferencesProvider.providePreferences();
        token = this.mPreferences.getString("token", "");


        Call<JsonObject> call_get = userService.getUserProfile(token);
        call_get.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("ERROR",response.code()+"");
                if(response.code()==200){
                    //response.body();
                    JsonObject userJson = response.body();
                    String nom_user = userJson.get("username").toString();
                    user.setText(atributs(nom_user));

                    String user_mail = userJson.get("email").toString();
                    mail.setText(atributs(user_mail));
                    String user_sex= userJson.get("genere").toString();
                    user_sex = user_sex.substring(1,user_sex.length()-1);
                    Log.d("TAG",user_sex);

                    if(user_sex.equals("M")){
                        Log.d("TAG","ENTRA");
                        sexo.setText("Hombre");
                    }
                    else{
                        sexo.setText("Mujer");
                    }
                    Log.d("TAG",user_sex);
                    String user_rol = userJson.get("rol").toString();
                    Log.d("rol",user_rol);
                    user_rol=user_rol.substring(1,user_rol.length()-1);
                    if(user_rol.equals("O")){

                        rols.setText("Organizador");
                    }

                    else{
                        rols.setText("Jugador");
                    }



                }
                else{
                    //missatge de error;
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG",t.getMessage().toString());
            }
        });
    }
    public String atributs(String n){

        n = n.substring(1,n.length()-1);

        return n;

    }

    public void cerrarSession(View view){

        this.mPreferences = PreferencesProvider.providePreferences();
        token = this.mPreferences.getString("token", "");


        userService = RetrofitClientInstance.
                getRetrofitInstance().create(UserService.class);

        Call<ResponseBody> call_delete= userService.deleteToken(token);
        call_delete.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                    Log.d("FUNC","entras?");

                        mPreferences.edit().putString("token", "").apply();
                        Intent intent = new Intent(Perfil.this, Login.class);
                        startActivity(intent);




                }



            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}