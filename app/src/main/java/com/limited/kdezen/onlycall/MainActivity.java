package com.limited.kdezen.onlycall;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final int REQUEST_CODE = 1234;
    private String callTXT;
    private ImageView imageFone;
    private TextView textNumero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //   callTXT="99999999";
        imageFone =(ImageView) findViewById(R.id.imagePhone);

         textNumero=(TextView) findViewById(R.id.textNumero);



        barHide();

        loadCall();

        PackageManager pm= getPackageManager();
        List<ResolveInfo> activities=pm.queryIntentActivities( new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),0);

        if(activities.size()==0){

            imageFone.setClickable(false);
            // speakButton.setText("Recognizer not present");
        }



        //onBackPressed();

       // actionBar.setDisplayHomeAsUp(true);
    }
    //aqui começa os metodos da recognição de voz
    public void speakButtonClicked(View v){
        startVoiceRecognitionActivity();
    }

    private void  startVoiceRecognitionActivity(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Detectando chamada...");
        startActivityForResult(intent,REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
           // wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matches));
        }

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && null != data) {

            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            callTXT = result.get(0);

            textNumero.setText(callTXT);

            if(callTXT=="caixa postal"){
                callTXT="*100";
            }


            Uri uri = Uri.parse("tel:" + callTXT);
            //   Intent callIntent = new Intent(Intent.ACTION_DIAL);
            Intent callIntent = new Intent(Intent.ACTION_CALL, uri);

            startActivity(callIntent);




        }
    }



    public void loadCall() {


        imageFone.setClickable(true);
        imageFone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startVoiceRecognitionActivity();



            }


        });

    }
        // aqui termina

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void barHide(){

        View decorView=getWindow().getDecorView();
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





}
