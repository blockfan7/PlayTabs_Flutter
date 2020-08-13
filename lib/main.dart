import 'package:flutter/material.dart';
import 'package:flutter_colorpicker/flutter_colorpicker.dart';
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
  TextEditingController currencyController = new TextEditingController();
  TextEditingController amountController = new TextEditingController();
  String errorText = "";
  Color currentColor = Color(0xff443a49);

  void changeColor(Color color) {
    setState(() => currentColor = color);
  }

  @override
  void initState() {
    super.initState();
    currencyController.text = "USD";
    amountController.text = "50.0";
  }

  void onClickSend() async {
    print("kakao login clicked");
    PaymentModel paymentInfo = new PaymentModel();
    paymentInfo.payButtonColor = '#${currentColor.value.toRadixString(16)}';
    paymentInfo.transactionAmount = double.parse(amountController.text);
    paymentInfo.currencyCode = currencyController.text.trim();
    nativeTransmitter.demoFunction(paymentInfo, (result) {
      setState(() {
        if (result == 0) {
          errorText = "Error";
        } else {
          errorText = "Success";
        }
      });

      print(result);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        width: double.infinity,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Container(
              margin: EdgeInsets.only(bottom: 20),
              width: 200,
              child: TextField(
                controller: currencyController,
                style: TextStyle(color: Colors.black),
                decoration: InputDecoration(
                  isDense: true,
                  hintText: "Type the currency (USD, BHD, INR, ...)",
                ),
              ),
            ),
            Container(
              margin: EdgeInsets.only(bottom: 20),
              width: 200,
              child: TextField(
                controller: amountController,
                keyboardType: TextInputType.number,
                style: TextStyle(color: Colors.black),
                decoration: InputDecoration(
                  isDense: true,
                  hintText: "Amount",
                ),
              ),
            ),
            RaisedButton(
              elevation: 3.0,
              onPressed: () {
                showDialog(
                  context: context,
                  builder: (BuildContext context) {
                    return AlertDialog(
                      titlePadding: const EdgeInsets.all(0.0),
                      contentPadding: const EdgeInsets.all(0.0),
                      content: SingleChildScrollView(
                        child: ColorPicker(
                          pickerColor: currentColor,
                          onColorChanged: changeColor,
                          colorPickerWidth: 300.0,
                          pickerAreaHeightPercent: 0.7,
                          enableAlpha: true,
                          displayThumbColor: true,
                          showLabel: true,
                          paletteType: PaletteType.hsv,
                          pickerAreaBorderRadius: const BorderRadius.only(
                            topLeft: const Radius.circular(2.0),
                            topRight: const Radius.circular(2.0),
                          ),
                        ),
                      ),
                    );
                  },
                );
              },
              child: const Text('Change Color'),
              color: currentColor,
              textColor: useWhiteForeground(currentColor)
                  ? const Color(0xffffffff)
                  : const Color(0xff000000),
            ),
            RaisedButton(
              elevation: 3.0,
              child: Text(
                "Purchase",
                style: TextStyle(color: Colors.black, fontSize: 20),
              ),
              onPressed: onClickSend,
            ),
            Container(
              margin: EdgeInsets.only(top: 30),
              child: Text(errorText),
            )
          ],
        ),
      ),
    );
  }
}
