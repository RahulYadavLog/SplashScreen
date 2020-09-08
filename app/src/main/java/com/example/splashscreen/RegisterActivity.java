package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText etEmail,etMobile,etPassword,etcoName;
    MaterialButton btnSignUp;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    boolean setErrorEmail=true,setErrorMobile=true,setErrorPass=true,setErrorCoName=true;
    private static final String TAG = "MainActivity";
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String fir_Mobile,fir_Email,fir_Password;
    boolean profile=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail=findViewById(R.id.edtEmail);
        etMobile=findViewById(R.id.edtMobileNumber);
        etPassword=findViewById(R.id.edtPassword);
        etcoName=findViewById(R.id.company_Name);
        btnSignUp=findViewById(R.id.btnSignUpCont);
        Intent intent=getIntent();
        profile=intent.getBooleanExtra("profile",false);
        if(profile==true) {
            GetData();
        }
/*        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {

                    if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {

                        Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();

                        etEmail.setError("Invalid email address");
                        setErrorEmail=true;
                    } else {
                        setErrorEmail=false;
/*
                        new CheckEmailId().execute(etEmail.getText().toString());
*/

                        Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });


        etMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (etMobile.getText().toString().length() < 10) {
                        setErrorMobile=true;
                        etMobile.setError("Wrong Mobile Number");
                        Toast.makeText(RegisterActivity.this, "Please Enter Correct Mobile Number", Toast.LENGTH_SHORT).show();
                    } else {

/*
                        new MobileNoVerify().execute(etMobile.getText().toString());
*/
                        setErrorMobile=false;
                    }
                }
            }
        });

        etcoName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!etcoName.getText().toString().isEmpty()) {
                        setErrorCoName=false;
                    } else {

                        setErrorCoName=true;
                        etcoName.setError("Wrong Mobile Number");
                        Toast.makeText(RegisterActivity.this, "Please Enter Correct Mobile Number", Toast.LENGTH_SHORT).show();


                    }
                }
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!setErrorEmail==true&&!setErrorMobile==true&&!setErrorCoName==true)
                {
                    if (!(etPassword.getText().toString().length() <  8 && !isValidPassword(etPassword.getText().toString()))) {

                        Intent intent = new Intent(RegisterActivity.this, OtpVerifyActivity.class);
                        intent.putExtra("number", etMobile.getText().toString());
                        intent.putExtra("email", etEmail.getText().toString());
                        intent.putExtra("pass", etPassword.getText().toString());
                        intent.putExtra("coName", etcoName.getText().toString());
                        startActivity(intent);
                        /*  new Registration().execute();*/
                    }
                    else {
                        System.out.println("Not Valid");
                        etPassword.setError("Password Enter Min Length 8 please use character and symbol ");
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Please Enter All Record", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

   /* public class MobileNoVerify extends AsyncTask<String, Void, String> {


        String errorNo = null, errorMess = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            JSONArray root = new JSONArray();
            JSONObject ob = new JSONObject();
            String json, pass, jsonStr = null, userResponse = null;
            try {
                ob.put("profile_mobile", params[0]);
                root.put(0, ob);
                json = root.toString();
                String url = MPROURL + "/Mpro.asmx/GetJsonDATA?QueryNo=11&sessionId="+"null"+"&JSONStringFormat1=";

                pass = url + json;

                Log.i(TAG, "doInBackground: pass:- " + pass);
                jsonStr = sh.makeServiceCall(pass);

                Log.i(TAG, "doInBackground: jsonStr:- " + jsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: Error in Sending Data:- ", e);
            }
            if (jsonStr != null) {

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray jsonArray = jsonObj.getJSONArray("tab_profile");
                    if (!jsonArray.isNull(0)) {
                        JSONObject object = jsonArray.getJSONObject(0);

                        errorNo = object.getString("error_number");
                        errorMess = object.getString("error_message");


                    }


                    Log.i(TAG, "doInBackground: errorNo:- " + errorNo);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "doInBackground: JsonCastError:- ", e);
                    errorNo = null;
                    errorMess = null;
                }
                return errorNo;

            } else {
                return errorNo;

            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {

                switch (s) {
                    case "1":
                        setErrorMobile = false;
                        break;
                    case "0":
                        setErrorMobile = true;
                        etMobile.setError("Mobile No already exists");
                        break;
                    default:
                        setErrorMobile = false;
                        break;


                }
            } else {
                setErrorMobile = false;
                Toast.makeText(RegisterActivity.this, "We Lost You! Check Network..", Toast.LENGTH_LONG).show();
            }

        }


    }

    public class CheckEmailId extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            JSONArray root = new JSONArray();
            JSONObject ob = new JSONObject();
            String json, pass, jsonStr = null, userResponse = null;
            try {
                ob.put("profile_email", params[0]);
                root.put(0, ob);
                json = root.toString();

                String url = MPROURL + "/Mpro.asmx/GetJsonDATA?QueryNo=8&sessionId="+"null"+"&JSONStringFormat1=";

                pass = url + json;

                Log.i(TAG, "doInBackground: pass:- " + pass);
                jsonStr = sh.makeServiceCall(pass);

                Log.i(TAG, "doInBackground: jsonStr:- " + jsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: Error in Sending Data:- ", e);
            }
            if (jsonStr != null) {

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    userResponse = jsonObj.optString("tab_profile");
                    Log.i(TAG, "doInBackground: response:- " + userResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "doInBackground: JsonCastError:- ", e);
                }
                return userResponse;

            } else {
                return null;

            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {

                switch (s) {
                    case "Email available":
                        setErrorEmail = false;
                        break;
                    case "Email already exists":
                        setErrorEmail = true;
                        etEmail.setError("Email already exists");
                        break;
                    default:
                        break;


                }
            } else {
                Toast.makeText(RegisterActivity.this, "We Lost You! Check Network..", Toast.LENGTH_LONG).show();
            }

        }


    }*/
    public void GetData()
    {

        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        DocumentReference docRef=firestore.collection("user").document(firebaseAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                fir_Email=documentSnapshot.getString("Email");
                etEmail.setText(fir_Email);
                fir_Mobile=(documentSnapshot.getString("Mobile_No"));
                etMobile.setText(fir_Mobile);
                fir_Password=(documentSnapshot.getString("Password"));
                etcoName.setText(documentSnapshot.getString("Company Name"));
                etPassword.setText(fir_Password);


            }
        });
    }
    }
}