package cat.udl.tidic.amb.tournmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import cat.udl.tidic.amb.tournmaster.preferences.PreferencesProvider;
import cat.udl.tidic.amb.tournmaster.services.UserService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private UserService userService;
    private EditText usernameET;
    private EditText passwordET;
    private SharedPreferences mPreferences;
    private String token;
    private TextView miss;
    private TextView miss_conx;
    private String username;
    private String pass;
    private ImageView imatge;
    private TextView miss_init;
    private TextView users;
    private Button volver;
    private TextView mail;
    private TextView passs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();

        volver = findViewById(R.id.btn_volver);


        userService = RetrofitClientInstance.
                getRetrofitInstance().create(UserService.class);


        // elements del layout:
        //----------------------

        //Instancia
        this.mPreferences = PreferencesProvider.providePreferences();

        usernameET = findViewById(R.id.nombre);
        passwordET = findViewById(R.id.contraseña);
        final TextView tokenTV = findViewById(R.id.text_token);
        final Button createToken = findViewById(R.id.inciarButton);
        TextView tokenLabel = findViewById(R.id.text_createToken);
        miss_conx = findViewById(R.id.miss_conex);
        mail= findViewById(R.id.text_mail);
        passs= findViewById(R.id.text_birthday);

        //users= findViewById(R.id.user);
        //imatge = findViewById(R.id.user_img);
        miss_init = findViewById(R.id.miss_inici);
        miss = findViewById(R.id.missatge_error);
        token = this.mPreferences.getString("token","");
        tokenLabel.setVisibility(View.INVISIBLE);
        tokenTV.setVisibility(View.INVISIBLE);
        //imatge.setVisibility(View.INVISIBLE);
        //users.setVisibility(View.INVISIBLE);
        miss.setVisibility(View.INVISIBLE);
        miss_init.setVisibility(View.INVISIBLE);
        miss_conx.setVisibility(View.INVISIBLE);
        //mail.setVisibility(View.INVISIBLE);
        //passs.setVisibility(View.INVISIBLE);



        usernameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                username = usernameET.getText().toString();
                if (username.isEmpty()){
                    createToken.setEnabled(false);
                }
                else{
                    passwordET.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            pass = passwordET.getText().toString();

                            if (pass.isEmpty()){
                                createToken.setEnabled(false);
                            }
                            else{
                                createToken.setEnabled(true);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (!token.equals("")){
            intent = new Intent(Login.this,Perfil.class);
            startActivity(intent);

            tokenLabel.setVisibility(View.INVISIBLE);
            tokenTV.setText(token);
            usernameET.setVisibility(View.INVISIBLE);
            passwordET.setVisibility(View.INVISIBLE);
            createToken.setVisibility(View.INVISIBLE);
            tokenTV.setVisibility(View.INVISIBLE);
            miss_init.setText(getResources().getString(R.string.Tittle));
            miss_init.setVisibility(View.VISIBLE);

            // users.setText(usernameET.getText().toString());
        }

        createToken.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                String token_decoded = (username + ":" + password);
                System.out.println(token_decoded);
                byte[] bytes = token_decoded.getBytes(StandardCharsets.UTF_8);
                String _token = Base64.encodeToString(bytes, Base64.DEFAULT);
                _token = ("Authentication: " + _token).trim();
                Call<ResponseBody> call_post = userService.createToken(_token);
                call_post.enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.body() != null) {
                            try {

                                String userToken = response.body().string().split(":")[1];
                                Log.d("MAIN", userToken);
                                userToken = userToken.substring(2,userToken.length()-2);
                                mPreferences.edit().putString("token", userToken).apply();
                                Intent intent = new Intent(Login.this,Perfil.class);
                                startActivity(intent);


                            } catch (IOException e) {
                                e.printStackTrace();


                            }
                        }
                        else{

                            miss.setText(getResources().getString(R.string.Error_Login));
                            miss.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("MAIN", t.getMessage());
                        //ficar error de conexió
                        miss_conx.setText(getResources().getString(R.string.Error_Conex));
                        miss_conx.setVisibility(View.VISIBLE);





                    }
                });


                Toast.makeText(getApplicationContext(),
                        "Token obtained properly", Toast.LENGTH_SHORT).show();
            }



        });
    }

    public void anterior(View view){
        Intent anterior = new Intent(Login.this, MainActivity.class);
        startActivity(anterior);

    }

}
