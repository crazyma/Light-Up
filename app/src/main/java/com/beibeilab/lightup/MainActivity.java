package com.beibeilab.lightup;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextIntro2;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupStatusBar();

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

    /**
     * https://stackoverflow.com/questions/22192291/how-to-change-the-status-bar-color-in-android
     */
    private void setupStatusBar(){
        Window window = getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.gray));
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
