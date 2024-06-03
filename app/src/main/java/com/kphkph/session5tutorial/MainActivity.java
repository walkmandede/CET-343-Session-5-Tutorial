package com.kphkph.session5tutorial;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();



    }

    private void initViews(){
        EditText txtInput = (EditText) findViewById(R.id.txtInput);
        TextView lblResult = (TextView) findViewById(R.id.lblResult);
        Button btnShowPreview = (Button) findViewById(R.id.btnShowPreview);
        Button btnNewActivity = (Button) findViewById(R.id.btnNewActivity);
        Button btnSendSms = (Button) findViewById(R.id.btnSendSms);
        Button btnSendEmail = (Button) findViewById(R.id.btnSendEmail);

        btnShowPreview.setEnabled(false);
        btnNewActivity.setEnabled(false);
        btnSendSms.setEnabled(false);
        btnSendEmail.setEnabled(false);

        txtInput.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        final String typedString = txtInput.getText().toString();
                        if(typedString.isEmpty()){
                            btnShowPreview.setEnabled(false);
                            btnNewActivity.setEnabled(false);
                            btnSendSms.setEnabled(false);
                            btnSendEmail.setEnabled(false);
                        }
                        else{
                            btnShowPreview.setEnabled(true);
                            btnNewActivity.setEnabled(true);
                            btnSendSms.setEnabled(true);
                            btnSendEmail.setEnabled(true);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
        );

        btnNewActivity.setOnClickListener(v -> {
            final String typedString = txtInput.getText().toString();
            Intent newActivityIntent = new Intent(getApplicationContext(),SecondActivity.class);
            newActivityIntent.putExtra("KEY_DATA",typedString);
            startActivity(newActivityIntent);
        });

        btnShowPreview.setOnClickListener(v -> {
            final String typedString = txtInput.getText().toString();
            lblResult.setText(typedString);
        });

        btnSendSms.setOnClickListener(v -> {
            final String typedString = txtInput.getText().toString();
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("smsto:1234567890")); // Set the recipient phone number
            smsIntent.putExtra("sms_body", typedString); // Set the message body
            startActivity(smsIntent);
        });

        btnSendEmail.setOnClickListener(v -> {
            final String typedString = txtInput.getText().toString();
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:testuser@example.com")); // Only email apps should handle this
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Test Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, typedString);

            startActivity(Intent.createChooser(emailIntent, "Choose an email client:"));

        });


    }


}