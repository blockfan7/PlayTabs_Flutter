class PaymentModel {
  int id = 0;
  String language = "en";

  String transactionTitle = "transactionTitle";
  double transactionAmount = 5000.0;

  String currencyCode = "INR";
  String customerPhoneNumber = "1548124";
  String customerEmail = "customerEmail@email.com";
  String orderId = "orderId";
  String productName = "productName";

  // Billing Address
  String addressBilling = "addressBilling";
  String cityBilling = "cityBilling";
  String stateBilling = "stateBilling";
  String countryBilling = "countryBilling";
  String postalCodeBilling = "00973";
  //Put Country Phone code if Postal code not available '00973'

  // Shipping Address
  String addressShipping = "addressShipping";
  String cityShipping = "cityShipping";
  String stateShipping = "stateShipping";
  String countryShipping = "countryShipping";
  String postalCodeShipping = "postalCodeShipping";
  // Payment Page Style
  String payButtonColor = "#FF0000";

  // Tokenization
  bool isTokenization = true;

  Map<String, dynamic> toJson() => {
        "id": id,
        "transactionTitle": transactionTitle,
        "transactionAmount": transactionAmount,
        "currencyCode": currencyCode,
        "customerPhoneNumber": customerPhoneNumber,
        "customerEmail": customerEmail,
        "orderId": orderId,
        "productName": productName,
        "addressBilling": addressBilling,
        "cityBilling": cityBilling,
        "stateBilling": stateBilling,
        "countryBilling": countryBilling,
        "postalCodeBilling": postalCodeBilling,
        "addressShipping": addressShipping,
        "cityShipping": cityShipping,
        "stateShipping": stateShipping,
        "countryShipping": countryShipping,
        "postalCodeShipping": postalCodeShipping,
        "payButtonColor": payButtonColor,
        "isTokenization": isTokenization,
      };
}
