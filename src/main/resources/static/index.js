$(document).ready(function(){
  exchangeRate();
});

$("#receipt-country").change(function () {
  exchangeRate(this.value);
})

$("#calculation").click(function () {
  let receiveCountry = $("#receipt-country option:selected").val();
  let remittanceAmount = $("#remittance-amount").val();

  if(!remittanceAmountValidation(remittanceAmount)){
    return false;
  }

  let request =  {
    receiveCountry : receiveCountry,
    remittanceAmount : remittanceAmount
  }

  $.ajax({
    url: "/receive-amount/calculation",
    method: "POST",
    data : JSON.stringify(request),
    dataType: "json",
    contentType: "application/json"
  })
  .done(function (result) {
    $("#receipt-amount").text("수취금액은 " + result.receivedAmount + " " +  $("#receipt-country option:selected").val().toUpperCase()+" 입니다.");
    if ($("#receipt-country option:selected").val() == "krw") {
      $("#exchange-rate").text(result.exchangeRate + " KRW/USD");
    }else if($("#receipt-country option:selected").val() == "jpy") {
      $("#exchange-rate").text(result.exchangeRate + " JPY/USD");
    }else {
      $("#exchange-rate").text(result.exchangeRate + " PHP/USD");
    }
  })
})

function exchangeRate() {
  $.ajax({
    url: "/exchange-rate",
    method: "GET",
    dataType: "json"
  })
  .done(function (result) {
    if ($("#receipt-country option:selected").val() == "krw") {
      $("#exchange-rate").text(numberWithCommas(result.krw, 2) + " KRW/USD");
    }else if($("#receipt-country option:selected").val() == "jpy") {
      $("#exchange-rate").text(numberWithCommas(result.jpy, 2) + " JPY/USD");
    }else {
      $("#exchange-rate").text(numberWithCommas(result.php, 2) + " PHP/USD");
    }
  })
}

function numberWithCommas(double, decimalPointCipher) {
  let parts = double.toString().split(".");
  parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  parts[1] = parts[1].substr(0, decimalPointCipher);

  if (decimalPointCipher < 1) {
    return parts[0];
  }
  else {
    return parts.join(".");
  }
}

function isNumber(number) {
  let numberRegex = /^[0-9]*$/;

  if (numberRegex.test(number)) {
    return true;
  }
  return false;
}

function remittanceAmountValidation(remittanceAmount) {
  let message = "송금액이 바르지 않습니다";

  if ( typeof remittanceAmount === "string") {
    if (remittanceAmount.length === 0 ) {
      alert(message);
      return false;
    }
  }

  if (!isNumber(remittanceAmount)) {
    alert(message);
    $("#remittance-amount").val("");
    return false;
  }

  if (remittanceAmount <= 0 || remittanceAmount > 10000) {
    alert(message);
    return false;
  }

  return true;
}