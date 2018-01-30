package com.pramudana.sam.spinnervisibility;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Spinner spinVisible;
    Button btnSubmit;
    EditText etLink, etNo, etEmail, etSubject, etPesan;
    String[] dataSpin = new String[] {

            "Email", "SMS", "Phone", "Web"
    };
    String itemSpin;
    String noTelp = "085831000192";

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinVisible = (Spinner)findViewById(R.id.spinVisible);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        etLink = (EditText)findViewById(R.id.etLink);
        etNo = (EditText)findViewById(R.id.etNo);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etSubject = (EditText)findViewById(R.id.etSubject);
        etPesan = (EditText)findViewById(R.id.etPesan);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSpin);
        spinVisible.setAdapter(adapter);

        spinVisible.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                itemSpin = parent.getItemAtPosition(position).toString();

                if (itemSpin == "Email") {

                    etEmail.setVisibility(View.VISIBLE);
                    etSubject.setVisibility(View.VISIBLE);
                    etPesan.setVisibility(View.VISIBLE);
                    etLink.setVisibility(View.GONE);
                    etNo.setVisibility(View.GONE);

                }else if (itemSpin == "SMS") {

                    etEmail.setVisibility(View.GONE);
                    etSubject.setVisibility(View.GONE);
                    etPesan.setVisibility(View.VISIBLE);
                    etLink.setVisibility(View.GONE);
                    etNo.setVisibility(View.VISIBLE);

                }else if (itemSpin == "Phone") {

                    etEmail.setVisibility(View.GONE);
                    etSubject.setVisibility(View.GONE);
                    etPesan.setVisibility(View.GONE);
                    etLink.setVisibility(View.GONE);
                    etNo.setVisibility(View.VISIBLE);

                }else if (itemSpin == "Web") {

                    etEmail.setVisibility(View.GONE);
                    etSubject.setVisibility(View.GONE);
                    etPesan.setVisibility(View.GONE);
                    etLink.setVisibility(View.VISIBLE);
                    etNo.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (itemSpin == "Phone"){

                    String sTelp = etNo.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + sTelp));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }else if (itemSpin == "SMS") {

                    String dataIsiSMS = "Hallo, Ini Adalah SMS";
                    String sTelp = etNo.getText().toString();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M); {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                                    == PackageManager.PERMISSION_DENIED) {

                                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                                String[] permissions = {Manifest.permission.SEND_SMS};

                                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
                            }
                        }
                    }

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                    //memanggil Library SMSManager dan Memanggil String dataIsiSMS
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(sTelp, null, dataIsiSMS, pi, null);

                    Toast.makeText(getApplicationContext(), "SMS Berhasil Dikirim", Toast.LENGTH_SHORT).show();
                }else if (itemSpin == "Email") {

                    String emailTujuan = "Sampramudana12@gmail.com";
                    String subjectEmail = "Dat Stick Brooh.";
                    String isiEmail = "You Know This Arvisco With Prince Film, Tanpa filter VSCO.";

                    //Intent Email
                    Intent nEmail = new Intent(Intent.ACTION_SEND);
                    nEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{emailTujuan});
                    nEmail.putExtra(Intent.EXTRA_SUBJECT, subjectEmail);
                    nEmail.putExtra(Intent.EXTRA_TEXT, isiEmail);

                    //format kode untuk pengiriman email
                    nEmail.setType("message/rfc822");
                    startActivity(Intent.createChooser(nEmail, "Choose Your Client Email"));
                }else if (itemSpin == "Web") {

                    String urlWeb = "https://www.bukalapak.com/";

                    //Memanggil URL Web ketika Intent
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlWeb));
                    startActivity(intent);
                }
            }
        });
    }
}
