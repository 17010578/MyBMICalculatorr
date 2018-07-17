package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etweight, etheight;
    Button btncalculate, btnreset;
    TextView tvdate, tvBMI, tvAdd;


    String datetime;
    float BMI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etweight = findViewById(R.id.editTextWeight);
        etheight = findViewById(R.id.editTextHeight);
        btncalculate = findViewById(R.id.buttonCalc);
        btnreset = findViewById(R.id.buttonReset);
        tvdate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvAdd = findViewById(R.id.textViewAdd);


        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strweight = etweight.getText().toString();
                float weight = Float.parseFloat(strweight);
                String strheight = etheight.getText().toString();
                float height = Float.parseFloat(strheight);

                BMI = weight / (height * height);
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH) + 1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                if (BMI < 18.5){
                    tvAdd.setText("You are underweight");
                } else if ( BMI >= 18.5 || BMI<=24.9){
                    tvAdd.setText("Your BMI is normal");
                }else if ( BMI>=25 || BMI<=29.9){
                    tvAdd.setText("You are overweight");
                }
                else if (BMI >30){
                    tvAdd.setText("You are obese");
                }

                tvdate.setText(getApplication().getText(R.string.date) +datetime);
                tvBMI.setText(getApplication().getText(R.string.bmi)+   Float.toString(BMI));
            }
        });

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etweight.setText(null);
                etheight.setText(null);
                tvdate.setText(null);
                tvBMI.setText(null);
                BMI = 0;
                datetime = null;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("datetime",datetime);
        prefEdit.putFloat("BMI",BMI);
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String savedate = prefs.getString("datetime","");
        Float saveBMI = prefs.getFloat("BMI",0);
        tvdate.setText(getApplication().getText(R.string.date) +savedate);
        tvBMI.setText(getApplication().getText(R.string.bmi)+   Float.toString(saveBMI));
    }
}
