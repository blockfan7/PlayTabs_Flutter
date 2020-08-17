package com.rajan.paytabs;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity;
import com.paytabs.paytabs_sdk.utils.PaymentParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {
    static Context context;
    private static final String payTabsChannelName = "com.rajan.paytabs.paytabs";
    private MethodChannel payTabsChannel;
    private String TAG = "PayTabsTAG";

//    private static final String merchant_email = "digital@marshal.ae";
//    private static final String secretKey = "WVEmL6gff30oiHyDgiau6tKje9FXhWieRXAC4QSjUTIdAgQRz1mGu6ZJgFHyB0NsXn51eXk9Q3mrkdjUeFAJMzt3ebBQboi8Moa0";
    private static final String merchant_email = "shildra7.dev@gmail.com";
    private static final String secretKey = "v2ZWdkbjFFLXosKHh2r7ZGHM1WzDsiUXkTTyd5RTLDR96BQnEiuDZbq180EAiyyDssbGy3zjHqtN2jaGCFkBRLCZLhA3P8HiBSY9";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "ON Create");
        Context context = getApplicationContext();
        CharSequence text = "ON Create";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        testBridge();
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

        Log.e(TAG, "On Configure Flutter Engine.");
        Context context = getApplicationContext();
        CharSequence text = "On Configure Flutter Engine.";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        context = getApplicationContext();

        // create PayTabs channel
        payTabsChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), payTabsChannelName);

        // define channel bridge
        payTabsChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
                try {
                    Log.e(TAG, "On Method Called." + call.method);
                    Context context = getApplicationContext();
                    CharSequence text = "On Method Called." + call.method;
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            // yourMethod();
                        }
                    }, 5000);

                    if (call.method.equals("demofunction")) {
                        demofunction(call.arguments, result);

                    } else {
                        result.notImplemented();
                    }

//
//                    Method method = MainActivity.class.getDeclaredMethod(
//                            call.method,
//                            Object.class,
//                            MethodChannel.Result.class
//                    );
//
//                    Log.e(TAG, "Method check....");
//                    text = "Method check....";
//                    toast = Toast.makeText(context, text, duration);
//                    toast.show();
//
//                    method.setAccessible(true);
//                    method.invoke(MainActivity.this, call.arguments, result);

                } catch (Throwable err) {
                    Log.e(TAG, "Exception during channel invoke", err);
                    result.error("Exception during channel invoke", err.getMessage(), null);
                }
            }
        });
    }

    // Callback List
    private Map<Integer, Runnable> payTabIdList = new HashMap<>();
    private int current;

    // demo function
    private void demofunction(Object args, MethodChannel.Result result) {
        try {
            Log.e(TAG, "demo function called.");
            Context context = getApplicationContext();
            CharSequence text = "demo function called.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();


            JSONObject argObj = new JSONObject(args.toString());
            int currentListenerId = argObj.getInt("id");
            current = currentListenerId;

            String language = PaymentParams.ENGLISH;
            String transactionTitle = argObj.getString("transactionTitle");
            double transactionAmount = argObj.getDouble("transactionAmount");

            String currencyCode = argObj.getString("currencyCode");
            String customerPhoneNumber = argObj.getString("customerPhoneNumber");
            String customerEmail = argObj.getString("customerEmail");
            String orderId = argObj.getString("orderId");
            String productName = argObj.getString("productName");

            // Billing Address
            String addressBilling = argObj.getString("addressBilling");
            String cityBilling = argObj.getString("cityBilling");
            String stateBilling = argObj.getString("stateBilling");
            String countryBilling = argObj.getString("countryBilling");
            String postalCodeBilling = argObj.getString("postalCodeBilling");  //Put Country Phone code if Postal code not available '00973'

            // Shipping Address
            String addressShipping = argObj.getString("addressShipping");
            String cityShipping = argObj.getString("cityShipping");
            String stateShipping = argObj.getString("stateShipping");
            String countryShipping = argObj.getString("countryShipping");
            String postalCodeShipping = argObj.getString("postalCodeShipping");
            // Payment Page Style
            String payButtonColor = argObj.getString("payButtonColor");

            // Tokenization
            Boolean isTokenization = argObj.getBoolean("isTokenization");


            final Handler handler = new Handler();
            payTabIdList.put(currentListenerId, new Runnable() {
                @Override
                public void run() {
                    if (payTabIdList.containsKey(currentListenerId)) {
                        Intent intent = new Intent(getApplicationContext(), PayTabActivity.class);

                        HashMap<String, Object> hashMap = new HashMap<String, Object>();
                        intent.putExtra("key", "value");
                        intent.putExtra(PaymentParams.MERCHANT_EMAIL, merchant_email);
                        intent.putExtra(PaymentParams.SECRET_KEY, secretKey);
                        intent.putExtra(PaymentParams.TRANSACTION_TITLE, transactionTitle);
                        intent.putExtra(PaymentParams.AMOUNT, (double)5.0);

                        intent.putExtra(PaymentParams.CURRENCY_CODE, currencyCode);
                        intent.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, customerPhoneNumber);
                        intent.putExtra(PaymentParams.CUSTOMER_EMAIL, customerEmail);
                        intent.putExtra(PaymentParams.ORDER_ID, orderId);
                        intent.putExtra(PaymentParams.PRODUCT_NAME, productName);

                        // Billing Address
                        intent.putExtra(PaymentParams.ADDRESS_BILLING, addressBilling);
                        intent.putExtra(PaymentParams.CITY_BILLING, cityBilling);
                        intent.putExtra(PaymentParams.STATE_BILLING, stateBilling);
                        intent.putExtra(PaymentParams.COUNTRY_BILLING, countryBilling);
                        intent.putExtra(PaymentParams.POSTAL_CODE_BILLING, postalCodeBilling);

                        // Shipping Address
                        intent.putExtra(PaymentParams.ADDRESS_SHIPPING, addressShipping);
                        intent.putExtra(PaymentParams.CITY_SHIPPING, cityShipping);
                        intent.putExtra(PaymentParams.STATE_SHIPPING, stateShipping);
                        intent.putExtra(PaymentParams.COUNTRY_SHIPPING, countryShipping);
                        intent.putExtra(PaymentParams.POSTAL_CODE_SHIPPING, postalCodeShipping);

                        // Payment Page Style
//                        intent.putExtra(PaymentParams.PAY_BUTTON_COLOR, payButtonColor);

                        // Tokenization
                        intent.putExtra(PaymentParams.IS_TOKENIZATION, true);

                        Log.e(TAG, "Run Intent");
                        Context context = getApplicationContext();
                        CharSequence text = "Run Intent";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        startActivityForResult(intent, PaymentParams.PAYMENT_REQUEST_CODE + currentListenerId);
                    }
                }
            });
            handler.post(payTabIdList.get(currentListenerId));
            result.success(null);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

     private void testBridge() {
         Intent intent = new Intent(getApplicationContext(), PayTabActivity.class);
         intent.putExtra("key", "value");
         intent.putExtra(PaymentParams.MERCHANT_EMAIL, merchant_email);
         intent.putExtra(PaymentParams.SECRET_KEY, secretKey);
         intent.putExtra(PaymentParams.TRANSACTION_TITLE, "transactionTitle");
         intent.putExtra(PaymentParams.AMOUNT, (double)5.0);

         intent.putExtra(PaymentParams.CURRENCY_CODE, "BHD");
         intent.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, "customerPhoneNumber");
         intent.putExtra(PaymentParams.CUSTOMER_EMAIL, "customerEmail");
         intent.putExtra(PaymentParams.ORDER_ID, "orderId");
         intent.putExtra(PaymentParams.PRODUCT_NAME, "productName");

         // Billing Address
         intent.putExtra(PaymentParams.ADDRESS_BILLING, "addressBilling");
         intent.putExtra(PaymentParams.CITY_BILLING, "cityBilling");
         intent.putExtra(PaymentParams.STATE_BILLING, "stateBilling");
         intent.putExtra(PaymentParams.COUNTRY_BILLING, "countryBilling");
         intent.putExtra(PaymentParams.POSTAL_CODE_BILLING, "postalCodeBilling");

         // Shipping Address
         intent.putExtra(PaymentParams.ADDRESS_SHIPPING, "addressShipping");
         intent.putExtra(PaymentParams.CITY_SHIPPING, "cityShipping");
         intent.putExtra(PaymentParams.STATE_SHIPPING, "stateShipping");
         intent.putExtra(PaymentParams.COUNTRY_SHIPPING, "countryShipping");
         intent.putExtra(PaymentParams.POSTAL_CODE_SHIPPING, "postalCodeShipping");

         // Payment Page Style
         intent.putExtra(PaymentParams.PAY_BUTTON_COLOR, "payButtonColor");

         // Tokenization
         intent.putExtra(PaymentParams.IS_TOKENIZATION, false);

         startActivityForResult(intent, PaymentParams.PAYMENT_REQUEST_CODE-1);
     }

    // Action Result Callback

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "Intent Callback");
        Context context = getApplicationContext();
        CharSequence text = "Intent Callback";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        if (requestCode >= PaymentParams.PAYMENT_REQUEST_CODE && requestCode <= PaymentParams.PAYMENT_REQUEST_CODE+1000) {

            int currentListenerId = requestCode - PaymentParams.PAYMENT_REQUEST_CODE;
            Map<String, Object> args = new HashMap();
            args.put("id", currentListenerId);
            if(data == null) {
                args.put("sentResult", false);
            }else{
                args.put("sentResult", true);
            }
//            payTabsChannel.invokeMethod("demofunction_callback", args);
//            payTabIdList.remove(current);
        }
    }
}
