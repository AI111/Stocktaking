package com.example.sasha.stocktaking;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sasha.stocktaking.repository.Item;
import com.example.sasha.stocktaking.repository.Place;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This sample performs continuous scanning, displaying the barcode and source image whenever
 * a barcode is scanned.
 */
public class ContinuousCaptureActivity extends Activity {
    private static final String TAG = ContinuousCaptureActivity.class.getSimpleName();
    private CompoundBarcodeView barcodeView;
    List<Item> items = new ArrayList<>();
    RadioGroup radioGroup;
    EditText editText;
    String resultat;
    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                resultat=result.getText();
                barcodeView.setStatusText(resultat);

            }
            barcodeView.pause();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.continuous_scan);
        editText = (EditText)findViewById(R.id.editText);
        radioGroup = (RadioGroup)findViewById(R.id.radio_groupe);
        barcodeView = (CompoundBarcodeView) findViewById(R.id.barcode_scanner);
        barcodeView.decodeContinuous(callback);
    }

    @Override
    protected void onResume() {
        super.onResume();

        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeView.pause();
    }

    @Override
    public void onBackPressed() {
        Log.d(MainActivity.LOG_TAG,"onBackPressed");
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Main2Activity.SER_KEY,  items.toArray(new Item[items.size()]));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
        //super.onBackPressed();
    }

    public void save(View view) {

            Item.State state=null;
            int id = radioGroup.getCheckedRadioButtonId();
            switch (id){
                case R.id.radioButton:
                    state = Item.State.BAD;
                    break;
                case R.id.radioButton2:
                    state= Item.State.MIDDLE;
                    break;
                case R.id.radioButton3:
                    state= Item.State.GOOD;
                    break;
                default:
                    Toast.makeText(getApplication(),"Chack State",Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d(MainActivity.LOG_TAG, " STATE = "+state+" invent_num = "+Integer.parseInt(resultat)+" "+resultat);
            items.add(new Item("EMPTY TITLE",editText.getText().toString(),state,Integer.parseInt(resultat),null,new Date()));
        editText.setText("");
        radioGroup.clearCheck();
        barcodeView.resume();

    }


    public void resume(View view) {
        barcodeView.resume();
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
