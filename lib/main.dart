import 'package:flutter/material.dart';
import 'package:paytabs/models/payment_model.dart';
import 'package:paytabs/services/native_transmitter.dart';

NativeTransmitter nativeTransmitter = new NativeTransmitter();
void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key}) : super(key: key);
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  void onClickSend() async {
    print("kakao login clicked");
    PaymentModel paymentInfo = new PaymentModel();
    nativeTransmitter.demoFunction(paymentInfo, (result) {
      print(result);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Container(),
            FlatButton(
              child: Text("Purchase"),
              onPressed: onClickSend,
            )
          ],
        ),
      ),
    );
  }
}
