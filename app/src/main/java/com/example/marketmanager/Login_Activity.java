package com.example.marketmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {
    private static String gmail;
    EditText edt_gmail, edt_password;
    Button btn_Login;
    TextView txt_nothaveAccount;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        Anhxa();
        EventAction();
    }

    private void EventAction() {
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gmail = edt_gmail.getText().toString();
                String password = edt_password.getText().toString();

                if(TextUtils.isEmpty(gmail))
                {
                    Toast.makeText(Login_Activity.this, "Please Enter Your Gmail", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(Login_Activity.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    auth.signInWithEmailAndPassword(gmail,password).addOnCompleteListener(Login_Activity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Login_Activity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(Login_Activity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

        txt_nothaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register_Activity.class));
            }
        });
    }

    private void Anhxa() {
        edt_gmail = findViewById(R.id.edt_log_email);
        edt_password = findViewById(R.id.edt_log_password);
        btn_Login = findViewById(R.id.btn_Login);
        txt_nothaveAccount = findViewById(R.id.txt_chuacotaikhoan);
        auth = FirebaseAuth.getInstance();
    }

    public static String getGmail(){
        return gmail;
    }

}