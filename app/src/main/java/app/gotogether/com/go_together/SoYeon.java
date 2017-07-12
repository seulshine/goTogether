package app.gotogether.com.go_together;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by so yeon on 2017-07-07.
 */

public class SoYeon extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soyeon);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_dial:
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:01020891228"));
                startActivity(i);
                break;
        }
    }
}
