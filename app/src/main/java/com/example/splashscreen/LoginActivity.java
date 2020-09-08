package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    MaterialButton loginButton,signup;
    EditText passwordEditText,usernameEditText;
    TextView logoText,titleText,forgetPass;
    ImageView logoImg;
    TextInputLayout email_text,password_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        signup = findViewById(R.id.sign_up);
        logoImg = findViewById(R.id.logoImg);
        logoText = findViewById(R.id.logoText);
        titleText = findViewById(R.id.titleText);
        forgetPass = findViewById(R.id.forget);
        email_text=findViewById(R.id.email_text_input);
        password_text=findViewById(R.id.password_text);
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                Pair[] pairs = new Pair[8];
                pairs[0] = new Pair<View, String>(logoImg, "logo_img");
                pairs[1] = new Pair<View, String>(logoText, "logo_name");
                pairs[2] = new Pair<View, String>(titleText, "logo_title");
                pairs[3] = new Pair<View, String>(email_text, "email");
                pairs[4] = new Pair<View, String>(password_text, "password");
                pairs[5] = new Pair<View, String>(loginButton, "login");
                pairs[6] = new Pair<View, String>(signup, "signup");
                pairs[7] = new Pair<View, String>(forgetPass, "forget");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });

    }
}