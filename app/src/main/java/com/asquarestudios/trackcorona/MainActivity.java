package com.asquarestudios.trackcorona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    dataVariable dataVariable = new dataVariable();
    private ImageView refreshButton;
    private TextView textView_totalCases,textView_recovered,textView_deaths,textView_activeCases;
    private ImageView imageView_flag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshButton = findViewById(R.id.button_refresh);
        refreshButton.setOnClickListener(this);
        connectViews();
        data ob  = new data();
        dataVariable = ob.getTime(new AsynCall() {
            @Override
            public void processFinished(dataVariable dataVariable)
            {

                updateData(dataVariable);

            }
        });
    }

    private void connectViews()
    {
        textView_activeCases=findViewById(R.id.textView_activeCases);
        textView_deaths=findViewById(R.id.textView_deaths);
        textView_recovered=findViewById(R.id.textView_recovered);
        textView_totalCases=findViewById(R.id.textView_totalCases);
        imageView_flag = findViewById(R.id.imageView_flag);
        imageView_flag.setOnClickListener(this);
    }

    private void openChrome()
    {
        Toast.makeText(MainActivity.this,"Opening Official Government website",Toast.LENGTH_SHORT).show();
        String url = "https://www.mohfw.gov.in/";
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setPackage("com.android.chrome");
        try {
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            // Chrome is probably not installed
            // Try with the default browser
            i.setPackage(null);
            startActivity(i);
        }
    }

    private void updateData(dataVariable dataVariable)
    {
        textView_totalCases.setText(dataVariable.getTotalCases());
        textView_recovered.setText(dataVariable.getRecovered());
        textView_deaths.setText(dataVariable.getDeaths());
        textView_activeCases.setText(dataVariable.getActiveCases());
    }
    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.button_refresh)
        {
            dataVariable = new data().getTime(new AsynCall() {
                @Override
                public void processFinished(dataVariable dataVariable)
                {

                    updateData(dataVariable);
                    Toast.makeText(MainActivity.this,"Refresh sucessfull",Toast.LENGTH_SHORT).show();
                    Log.d("error", "refreshed "+dataVariable.getLatestUpdate());
                }
            });
        }
        else if(view.getId()==R.id.imageView_flag)
        {
            openChrome();
        }


    }
}
