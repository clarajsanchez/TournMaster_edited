package cat.udl.tidic.amb.tournmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private Button register;


    //TODO: Hay 3 errores, mira el log de l'app para solucionar, hint: shared preferences y conexión con l'API.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.inciarButton);
        register = findViewById(R.id.registerButton);


    }
    public void login(View view){

        Intent intent = new Intent(MainActivity.this,Login.class);
        startActivity(intent);



    }

    public void register(View view){

        Intent intent = new Intent(MainActivity.this,Register.class);
        startActivity(intent);



    }


}
