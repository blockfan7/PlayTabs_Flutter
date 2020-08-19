//
//  MainViewController.swift
//  Runner
//
//  Created by Shildra on 8/13/20.
//

import Foundation

struct PaymentModel: Decodable{
    let id: Int
    let language: String
    let transactionTitle: String
    let transactionAmount: Double
    let currencyCode: String
    let customerPhoneNumber: String
    let customerEmail: String
    let orderId: String
    let productName: String
    let addressBilling: String
    let cityBilling: String
    let stateBilling: String
    let countryBilling: String
    let postalCodeBilling: String
    let addressShipping: String
    let cityShipping: String
    let stateShipping: String
    let countryShipping: String
    let postalCodeShipping: String
    let payButtonColor: String
    let isTokenization: Bool
}

class MainViewController: FlutterViewController {
    var payTabChannelName = "com.rajan.paytabs.paytabs"
    var initialSetupViewController: PTFWInitialSetupViewController!
    var channelListenerId : Int = 0
    var paytabChannel:FlutterMethodChannel!
    override func viewDidLoad() {
        super.viewDidLoad()
//        let controller : FlutterViewController = window?.rootViewController as! FlutterViewController
           paytabChannel = FlutterMethodChannel(name: payTabChannelName, binaryMessenger: self.binaryMessenger)
           paytabChannel.setMethodCallHandler({[weak self] (call: FlutterMethodCall, result: FlutterResult)-> Void in
               guard call.method == "demofunction" else{
                   result(FlutterMethodNotImplemented)
                   return
               }
        
            self?.demofunction(arguments: call.arguments as? String ?? "", flutterResult: result)
           })
        
    }
    
    private func demofunction(arguments: String, flutterResult: FlutterResult) {
        guard let tmp = (arguments).data(using: .utf8) else { return }
        do {
            let json = try JSONSerialization.jsonObject(with: tmp, options: .mutableContainers) as? [String:AnyObject]
                print("JSOn: ", (json?["id"] as? Int) ?? 0)
                print("JSOn: ", json?["customerEmail"] as! String)
                print("JSOn: ", (json?["transactionAmount"] as? Double) ?? 0)


            channelListenerId = (json?["id"] as? Int) ?? 0;

            let bundle = Bundle(url: Bundle.main.url(forResource: "Resources", withExtension: "bundle")!)

            self.initialSetupViewController = PTFWInitialSetupViewController.init(
                bundle: bundle,
                andWithViewFrame: self.view.frame,
                andWithAmount: (json?["transactionAmount"] as? Float) ?? 0.0,
                andWithCustomerTitle: (json?["transactionTitle"] as? String) ?? "" ,
                andWithCurrencyCode: (json?["currencyCode"] as? String) ?? "",
                andWithTaxAmount: 0.0,
                andWithSDKLanguage: (json?["language"] as? String) ?? "",

                andWithShippingAddress: (json?["addressShipping"] as? String) ?? "",
                andWithShippingCity: (json?["cityShipping"] as? String) ?? "",
                andWithShippingCountry: (json?["countryShipping"] as? String) ?? "",
                andWithShippingState: (json?["stateShipping"] as? String) ?? "",
                andWithShippingZIPCode:(json?["postalCodeShipping"] as? String) ?? "",

                andWithBillingAddress: (json?["addressBilling"] as? String) ?? "",
                andWithBillingCity:(json?["cityBilling"] as? String) ?? "",
                andWithBillingCountry: (json?["countryBilling"] as? String) ?? "",
                andWithBillingState: (json?["stateBilling"] as? String) ?? "",
                andWithBillingZIPCode: (json?["postalCodeBilling"] as? String) ?? "",

                andWithOrderID: (json?["orderId"] as? String) ?? "",
                andWithPhoneNumber: (json?["customerPhoneNumber"] as? String) ?? "",
                andWithCustomerEmail: (json?["customerEmail"] as? String) ?? "",
                andIsTokenization:false,
                andIsPreAuth: false,
                andWithMerchantEmail: "shildra7.dev@gmail.com",
                andWithMerchantSecretKey: "v2ZWdkbjFFLXosKHh2r7ZGHM1WzDsiUXkTTyd5RTLDR96BQnEiuDZbq180EAiyyDssbGy3zjHqtN2jaGCFkBRLCZLhA3P8HiBSY9",
                andWithAssigneeCode: "SDK",
                andWithThemeColor:UIColor.blue,
                andIsThemeColorLight: false
            )

            self.initialSetupViewController.didReceiveBackButtonCallback = {
            }

            self.initialSetupViewController.didStartPreparePaymentPage = {
            }
            self.initialSetupViewController.didFinishPreparePaymentPage = {
            }

            self.initialSetupViewController.didReceiveFinishTransactionCallback = {(responseCode, result, transactionID, tokenizedCustomerEmail, tokenizedCustomerPassword, token, transactionState) in
                  var args = [String: Any]()
                args["id"] = self.channelListenerId
                  args["sentResult"] = result
                  // todo - add other arg members
                self.paytabChannel.invokeMethod("demofunction_callback", arguments: args)
                            
    //            flutterResult(String(result))
            }

            self.view.addSubview(initialSetupViewController.view)
            self.addChild(initialSetupViewController)

            initialSetupViewController.didMove(toParent: self)
            flutterResult("")
        } catch {
           print("Something went wrong")
        }

    }
    
}
