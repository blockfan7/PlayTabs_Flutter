import 'dart:convert';

import 'package:flutter/services.dart';
import 'package:paytabs/models/payment_model.dart';

typedef void MultiUseCallback(dynamic msg);

class NativeTransmitter {
  // define channel
  static String payTabsChannelName = "com.rajan.paytabs/payTabs";
  MethodChannel payTabsChannel = MethodChannel(payTabsChannelName);

  // define the identifier to
  int _nextCallbackId = 0; // current callback
  Map<int, MultiUseCallback> payTabIdList = new Map(); // callback list

  // callback from native code
  Future<void> _methodCallHandler(MethodCall call) async {
    switch (call.method) {
      case 'demoFunction_callback': // callback from native code that was sent by demoFunction
        payTabIdList[call.arguments["id"]](call.arguments["sentResult"]);
        // remove the identifier
        payTabIdList.remove(call.arguments["id"]);
        break;
      default:
        print(
            'TestFairy: Ignoring invoke from native. This normally shouldn\'t happen.');
    }
  }

  // call demoFunction
  Future demoFunction(
      PaymentModel paymentInfo, MultiUseCallback callback) async {
    payTabsChannel.setMethodCallHandler(_methodCallHandler);
    int currentListenerId = _nextCallbackId++;
    paymentInfo.id = currentListenerId;
    payTabIdList[currentListenerId] = callback;
    await payTabsChannel.invokeMethod("demoFunction", jsonEncode(paymentInfo));
  }
}
