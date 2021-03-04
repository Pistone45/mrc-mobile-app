package com.pistonesanjama.mrc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class register extends AppCompatActivity {

    TextInputEditText txtfirstname, txtlastname, txtphone, txtemail, txtusername, txtpassword;
    Button register_btn;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtfirstname = findViewById(R.id.txtfirstname);
        txtlastname = findViewById(R.id.txtlastname);
        txtphone = findViewById(R.id.txtphone);
        txtemail = findViewById(R.id.txtemail);
        txtusername = findViewById(R.id.txtusername);
        txtpassword = findViewById(R.id.txtpassword);
        register_btn = findViewById(R.id.register_btn);
        db = new DatabaseHelper(this);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = txtfirstname.getText().toString();
                String lastname = txtlastname.getText().toString();
                String phone = txtphone.getText().toString();
                String email = txtemail.getText().toString();
                String username = txtusername.getText().toString();
                String password = txtpassword.getText().toString();

                if (firstname.equals("") || lastname.equals("") || phone.equals("") || email.equals("") || username.equals("") || password.equals("")){
                    Toast.makeText(register.this, "Please fil all fields to continue", Toast.LENGTH_SHORT).show();
                }else {
                    boolean checkUsername = db.checkUsername(username);
                    if (checkUsername == false) {
                        boolean insert = db.insertData(firstname, lastname, email, phone, username, password);
                        if (insert == true) {
                            Snackbar.make(v, "Registration Successful", Snackbar.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        } else {
                            Snackbar.make(v, "Registration Failed", Snackbar.LENGTH_LONG).show();
                        }
                    }else {
                        Snackbar.make(v, "User Already exists", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.botttom_navigation);
        //Set Selected
        bottomNavigationView.setSelectedItemId(R.id.register);

        //On Click Listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.login) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    Toast.makeText(register.this, "Fill Username and Password to Login", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }
}