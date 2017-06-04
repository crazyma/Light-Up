package com.beibeilab.lightup;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextIntro2;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextIntro2 = (TextView) findViewById(R.id.text_inro2);
        mButton = (Button) findViewById(R.id.button);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkSystemCanWrite()){
            mTextIntro2.setText(R.string.intro_message3);
            mButton.setVisibility(View.GONE);
        }else{
            mTextIntro2.setText(R.string.intro_message2);
            mButton.setVisibility(View.VISIBLE);
        }
    }

    private boolean checkSystemCanWrite(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.System.canWrite(this);
        }
        return true;
    }

    private void askSystemPermission(){
        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void buttonClicked(View view) {
        askSystemPermission();
    }
}
