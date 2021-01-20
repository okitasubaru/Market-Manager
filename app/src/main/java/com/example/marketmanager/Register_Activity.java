package com.example.marketmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.marketmanager.models.Account;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Activity extends AppCompatActivity {

    EditText edt_gmail, edt_password;
    Button btn_Regis;
    TextView txt_HaveAccount;
    DatabaseReference dbbaseReference;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        Anhxa();
        EventAction();

    }

    private void EventAction() {
        btn_Regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gmail = edt_gmail.getText().toString();
                String password = edt_password.getText().toString();

                if(TextUtils.isEmpty(gmail))
                {
                    Toast.makeText(Register_Activity.this, "Please Enter Your Gmail", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(Register_Activity.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                }

                auth.createUserWithEmailAndPassword(gmail,password).addOnCompleteListener(Register_Activity.this, task -> {
                    if(task.isSuccessful())
                    {
                        Account account = new Account(gmail,password);
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(account).addOnCompleteListener(task1 -> {
                            Toast.makeText(Register_Activity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Login_Activity.class));
                        });
                    }
                    else
                    {
                        Toast.makeText(Register_Activity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });

        txt_HaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login_Activity.class));
            }
        });
    }

    private void Anhxa() {
        edt_gmail = findViewById(R.id.edt_regis_email);
        edt_password = findViewById(R.id.edt_regis_password);
        btn_Regis = findViewById(R.id.btn_Regis);
        txt_HaveAccount = findViewById(R.id.txt_dacotaikhoan);
        dbbaseReference = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();
    }

}