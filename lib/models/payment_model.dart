class PaymentModel {
  int id = 0;
  String language = "en";

  String transactionTitle = "transactionTitle";
  double transactionAmount = 50.0;

  String currencyCode = "USD";
  String customerPhoneNumber = "0097333109781";
  String customerEmail = "customerEmail@email.com";
  String orderId = "12345";
  String productName = "productName";

  // Billing Address
  String addressBilling = "Manama";
  String cityBilling = "Manama";
  String stateBilling = "Manama";
  String countryBilling = "BHR";
  String postalCodeBilling = "12345";
  //Put Country Phone code if Postal code not available '00973'

  // Shipping Address
  String addressShipping = "Manama";
  String cityShipping = "Manama";
  String stateShipping = "Manama";
  String countryShipping = "BHR";
  String postalCodeShipping = "00973";
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
