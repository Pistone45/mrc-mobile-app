package com.pistonesanjama.mrc;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button loginbtn;
    TextInputEditText username, password;
    SharedPreferences pref;
    DatabaseHelper db;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbtn = findViewById(R.id.loginbtn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        db = new DatabaseHelper(this);
        progressBar = findViewById(R.id.progressbar);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                    SystemClock.sleep(4000);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
            }
        });

        if (pref.contains("username") && pref.contains("password")){
            thread.start();
        }

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("username", user);
                editor.putString("password", pass);
                editor.commit();

                if (user.equals("") || pass.equals("")){
                    Toast.makeText(MainActivity.this, "Please fil all fields to continue", Toast.LENGTH_SHORT).show();
                }else{
                    boolean checkuserpass = db.checkUsernamePassword(user, pass);
                    if (checkuserpass == true){
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }else {
                        Snackbar.make(v, "Invalid Credentials", Snackbar.LENGTH_LONG).show();
                    }
                }

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.botttom_navigation);
        //Set Selected
        bottomNavigationView.setSelectedItemId(R.id.login);

        //On Click Listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.register){
                    startActivity(new Intent(getApplicationContext(), register.class));
                    overridePendingTransition(0, 0);
                    Toast.makeText(MainActivity.this, "Fill in your information to register", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }
}