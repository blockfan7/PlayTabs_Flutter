package com.rajan.paytabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

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
    private static final String payTabsChannelName = "com.rajan.paytabs/payTabs";
    private MethodChannel payTabsChannel;
    private String TAG = "PayTabsTAG";

    private static final String merchant_email = "shildra7.dev@gmail.com";
    private static final String secretKey = "v2ZWdkbjFFLXosKHh2r7ZGHM1WzDsiUXkTTyd5RTLDR96BQnEiuDZbq180EAiyyDssbGy3zjHqtN2jaGCFkBRLCZLhA3P8HiBSY9";

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        context = getApplicationContext();

        // create PayTabs channel
        payTabsChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), payTabsChannelName);

        // define channel bridge
        payTabsChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
                try {
                    Method method = MainActivity.class.getDeclaredMethod(
                            call.method,
                            Object.class,
                            MethodChannel.Result.class
                    );

                    method.setAccessible(true);
                    method.invoke(MainActivity.this, call.arguments, result);
                } catch (Throwable err) {
                    Log.e(TAG, "Exception during channel invoke", err);
                    result.error("Exception during channel invoke", err.getMessage(), null);
                }
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        testBridge();
    }

    // Callback List
    private Map<Integer, Runnable> payTabIdList = new HashMap<>();
    private int current;

    // demo function
    private void demoFunction(Object args, MethodChannel.Result result) {
        try {
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

                        HashMap<String, Object> hashMap = new HashMap<String, Object>();
                        hashMap.put(PaymentParams.MERCHANT_EMAIL, merchant_email);
                        hashMap.put(PaymentParams.SECRET_KEY, secretKey);
                        hashMap.put(PaymentParams.LANGUAGE, language);
                        hashMap.put(PaymentParams.TRANSACTION_TITLE, transactionTitle);
                        hashMap.put(PaymentParams.AMOUNT, transactionAmount);

                        hashMap.put(PaymentParams.CURRENCY_CODE, currencyCode);
                        hashMap.put(PaymentParams.CUSTOMER_PHONE_NUMBER, customerPhoneNumber);
                        hashMap.put(PaymentParams.CUSTOMER_EMAIL, customerEmail);
                        hashMap.put(PaymentParams.ORDER_ID, orderId);
                        hashMap.put(PaymentParams.PRODUCT_NAME, productName);

                        // Billing Address
                        hashMap.put(PaymentParams.ADDRESS_BILLING, addressBilling);
                        hashMap.put(PaymentParams.CITY_BILLING, cityBilling);
                        hashMap.put(PaymentParams.STATE_BILLING, stateBilling);
                        hashMap.put(PaymentParams.COUNTRY_BILLING, countryBilling);
                        hashMap.put(PaymentParams.POSTAL_CODE_BILLING, postalCodeBilling);

                        // Shipping Address
                        hashMap.put(PaymentParams.ADDRESS_SHIPPING, addressShipping);
                        hashMap.put(PaymentParams.CITY_SHIPPING, cityShipping);
                        hashMap.put(PaymentParams.STATE_SHIPPING, stateShipping);
                        hashMap.put(PaymentParams.COUNTRY_SHIPPING, countryShipping);
                        hashMap.put(PaymentParams.POSTAL_CODE_SHIPPING, postalCodeShipping);

                        // Payment Page Style
                        hashMap.put(PaymentParams.PAY_BUTTON_COLOR, payButtonColor);

                        // Tokenization
                        hashMap.put(PaymentParams.IS_TOKENIZATION, isTokenization);
                        hashMap.put("currentListenerId", currentListenerId);

                        Intent intent = new Intent(getApplicationContext(), BridgeActivity.class);
                        intent.putExtra("params", hashMap);
                        startActivityForResult(intent, PaymentParams.PAYMENT_REQUEST_CODE);
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
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("key", "value");
        hashMap.put(PaymentParams.MERCHANT_EMAIL, merchant_email);
        hashMap.put(PaymentParams.SECRET_KEY, secretKey);
        hashMap.put(PaymentParams.TRANSACTION_TITLE, "transactionTitle");
        hashMap.put(PaymentParams.AMOUNT, (double)5.0);

        hashMap.put(PaymentParams.CURRENCY_CODE, "BHD");
        hashMap.put(PaymentParams.CUSTOMER_PHONE_NUMBER, "customerPhoneNumber");
        hashMap.put(PaymentParams.CUSTOMER_EMAIL, "customerEmail");
        hashMap.put(PaymentParams.ORDER_ID, "orderId");
        hashMap.put(PaymentParams.PRODUCT_NAME, "productName");

        // Billing Address
        hashMap.put(PaymentParams.ADDRESS_BILLING, "addressBilling");
        hashMap.put(PaymentParams.CITY_BILLING, "cityBilling");
        hashMap.put(PaymentParams.STATE_BILLING, "stateBilling");
        hashMap.put(PaymentParams.COUNTRY_BILLING, "countryBilling");
        hashMap.put(PaymentParams.POSTAL_CODE_BILLING, "postalCodeBilling");

        // Shipping Address
        hashMap.put(PaymentParams.ADDRESS_SHIPPING, "addressShipping");
        hashMap.put(PaymentParams.CITY_SHIPPING, "cityShipping");
        hashMap.put(PaymentParams.STATE_SHIPPING, "stateShipping");
        hashMap.put(PaymentParams.COUNTRY_SHIPPING, "countryShipping");
        hashMap.put(PaymentParams.POSTAL_CODE_SHIPPING, "postalCodeShipping");

        // Payment Page Style
        hashMap.put(PaymentParams.PAY_BUTTON_COLOR, "payButtonColor");

        // Tokenization
        hashMap.put(PaymentParams.IS_TOKENIZATION, true);
        hashMap.put("currentListenerId", 5);

        Intent intent = new Intent(getApplicationContext(), BridgeActivity.class);
        intent.putExtra("params", hashMap);
        startActivityForResult(intent, PaymentParams.PAYMENT_REQUEST_CODE);
    }

    // Action Result Callback

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode >= PaymentParams.PAYMENT_REQUEST_CODE) {
            Map<String, Object> args = new HashMap();
            args.put("id", data.getIntExtra("id", 0));
            args.put("sentResult", data.getIntExtra("resultCode", 0));
            payTabsChannel.invokeMethod("demoFunction_callback", args);
            payTabIdList.remove(current);
        }
    }
}
