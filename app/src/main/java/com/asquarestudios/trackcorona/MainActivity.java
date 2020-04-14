package com.asquarestudios.trackcorona;

import androidx.appcompat.app.AppCompatActivity;

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
                Log.d("error", "first time "+dataVariable.getActiveCases());
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
                    Log.d("error", "refresh clicked "+dataVariable.getActiveCases());
                    updateData(dataVariable);
                    Toast.makeText(MainActivity.this,"Refresh sucessfull",Toast.LENGTH_SHORT).show();

                }
            });
        }



    }
}
