package com.sarmale.arduinobtexampleledcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class ConfigureLed extends AppCompatActivity {
    private static final String TAG = "FrugalLogs";
    TextView tvProgressLabelRed,tvProgressLabelGreen,tvProgressLabelBlue;
    static final  char RED='R';
    static final  char GREEN='G';
    static final  char BLUE='B';
    int redNumber, greenNumber, blueNumber;
    ConnectedThread connectedThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_led);
        SeekBar seekBarRed = findViewById(R.id.seekBarRed);
        SeekBar seekBarGreen = findViewById(R.id.seekBarGreen);
        SeekBar seekBarBlue = findViewById(R.id.seekBarBlue);
        ImageView colorDemo = findViewById(R.id.colorDemo);
        redNumber=greenNumber=blueNumber=0;
        colorDemo.setBackgroundColor(Color.rgb(redNumber, greenNumber, blueNumber));
        tvProgressLabelRed = findViewById(R.id.redValue);
        tvProgressLabelGreen = findViewById(R.id.greenValue);
        tvProgressLabelBlue = findViewById(R.id.blueValue);
        seekBarRed.setTag(RED);
        seekBarGreen.setTag(GREEN);
        seekBarBlue.setTag(BLUE);
        Log.d(TAG, "Launching ConfigureLed Activity");

        // set a change listener on the SeekBar
        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // updated continuously as the user slides the thumb
                char color = (char) seekBar.getTag();
                switch(color) {
                    case RED:
                        tvProgressLabelRed.setText("Red: " + progress);
                        redNumber=progress;
                        break;
                    case GREEN:
                        tvProgressLabelGreen.setText("Green: " + progress);
                        greenNumber=progress;
                        break;
                    case BLUE:
                        tvProgressLabelBlue.setText("Blue: " + progress);
                        blueNumber=progress;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + color);
                }

                colorDemo.setBackgroundColor(Color.rgb(redNumber, greenNumber, blueNumber));

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // called when the user first touches the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // called after the user finishes moving the SeekBar
                String complete = String.valueOf(redNumber)  + "." + String.valueOf(greenNumber) +"."+String.valueOf(blueNumber);
                connectedThread.write(complete);

            }
        };

        seekBarRed.setOnSeekBarChangeListener(seekBarChangeListener);
        seekBarGreen.setOnSeekBarChangeListener(seekBarChangeListener);
        seekBarBlue.setOnSeekBarChangeListener(seekBarChangeListener);

        int progress = seekBarRed.getProgress();
        tvProgressLabelRed.setText("Red: " + progress);
        tvProgressLabelGreen.setText("Green: " + progress);
        tvProgressLabelBlue.setText("Blue: " + progress);

        connectedThread = MyApplication.getApplication().getCurrentConnectedThread();
        Log.d(TAG, "");
    }
}