package com.rajan.paytabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity;
import com.paytabs.paytabs_sdk.utils.PaymentParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class BridgeActivity extends Activity {


    private int currentID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        HashMap<String, Object> hashMap = (HashMap<String, Object>)intent.getSerializableExtra("params");
        Log.e("params", hashMap.toString());

        currentID = (int) hashMap.get("currentListenerId");

        Intent in = new Intent(getApplicationContext(), PayTabActivity.class);
        for ( String key : hashMap.keySet() ) {
              if (hashMap.get(key) instanceof String) {
                  in.putExtra(key, (String) hashMap.get(key));
              } else if (hashMap.get(key) instanceof Double) {
                  in.putExtra(key, (Double) hashMap.get(key));
              } else if (hashMap.get(key) instanceof Boolean) {
                  in.putExtra(key, (Boolean) hashMap.get(key));
              } else if (hashMap.get(key) instanceof Float) {
                  in.putExtra(key, (Float) hashMap.get(key));
              }
        }

        startActivityForResult(in, PaymentParams.PAYMENT_REQUEST_CODE);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // Action Result Callback

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {
            Intent result = new Intent();
            result.putExtra("id", currentID);
            result.putExtra("resultCode", resultCode);
            setResult(requestCode, result);
            finish();
        }
    }
}
