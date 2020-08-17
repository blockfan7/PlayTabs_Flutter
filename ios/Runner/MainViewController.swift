//
//  MainViewController.swift
//  Runner
//
//  Created by Shildra on 8/13/20.
//

import Foundation

class MainViewController: FlutterViewController {
    
    var initialSetupViewController: PTFWInitialSetupViewController!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        let bundle = Bundle(url: Bundle.main.url(forResource: "Resources", withExtension: "bundle")!)
        self.initialSetupViewController = PTFWInitialSetupViewController.init(
            bundle: bundle,
            andWithViewFrame: self.view.frame,
            andWithAmount: 5.0,
            andWithCustomerTitle: "PayTabs Sample App",
            andWithCurrencyCode: "USD",
            andWithTaxAmount: 0.0,
            andWithSDKLanguage: "en",
            andWithShippingAddress: "Manama",
            andWithShippingCity: "Manama",
            andWithShippingCountry: "BHR",
            andWithShippingState: "Manama",
            andWithShippingZIPCode: "123456",
            andWithBillingAddress: "Manama",
            andWithBillingCity: "Manama",
            andWithBillingCountry: "BHR",
            andWithBillingState: "Manama",
            andWithBillingZIPCode: "12345",
            andWithOrderID: "12345",
            andWithPhoneNumber: "0097333109781",
            andWithCustomerEmail: "rhegazy@paytabs.com",
            andIsTokenization:false,
            andIsPreAuth: false,
            andWithMerchantEmail: "shildra7.dev@gmail.com",
            andWithMerchantSecretKey: "v2ZWdkbjFFLXosKHh2r7ZGHM1WzDsiUXkTTyd5RTLDR96BQnEiuDZbq180EAiyyDssbGy3zjHqtN2jaGCFkBRLCZLhA3P8HiBSY9",
            andWithAssigneeCode: "SDK",
            andWithThemeColor:UIColor.red,
            andIsThemeColorLight: false)
        
        
        self.initialSetupViewController.didReceiveBackButtonCallback = {
            
        }
        
        self.initialSetupViewController.didStartPreparePaymentPage = {
            // Start Prepare Payment Page
            // Show loading indicator
        }
        self.initialSetupViewController.didFinishPreparePaymentPage = {
            // Finish Prepare Payment Page
            // Stop loading indicator
        }
        
        self.initialSetupViewController.didReceiveFinishTransactionCallback = {(responseCode, result, transactionID, tokenizedCustomerEmail, tokenizedCustomerPassword, token, transactionState) in
            print("Response Code: \(responseCode)")
            print("Response Result: \(result)")
            
            // In Case you are using tokenization
            print("Tokenization Cutomer Email: \(tokenizedCustomerEmail)");
            print("Tokenization Customer Password: \(tokenizedCustomerPassword)");
            print("TOkenization Token: \(token)");
        }

        self.view.addSubview(initialSetupViewController.view)
        self.addChild(initialSetupViewController)
        
        initialSetupViewController.didMove(toParent: self)
    }
}
