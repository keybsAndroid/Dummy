package com.royalcommission.bs.modules.utils;

/**
 * Created by Prashant on 7/18/2018.
 */
public class Constants {

    public static final String NOTIFICATION = "Notification";
    public static final String IN_PATIENT_CATEGORY = "I";
    public static final String SERVICE_NUMBER = "";
    public static final String SERVICE_CONDITION = "";
    public static final String OTP_DELIMITER = ":";
    public static final String BROADCAST_INDOOR = "BROADCAST_INDOOR";
    public static final String TV_PACKAGE = "com.Pad.tvapp";
    public static int NUMBER_OF_CHECKBOX_COLUMNS = 3;
    public static int NUMBER_OF_COLUMNS = 4;
    public static int NUMBER_OF_DOCTOR_DASHBOARD_COLUMNS = 2;
    public static int NUMBER_OF_MEALS_SELECTION_COLUMNS = 2;
    public static int NUMBER_OF_MEALS_COLUMNS = 3;
    public static int NUMBER_OF_TODAY_MEDICINE_COLUMNS = 5;
    public static int NUMBER_OF_APPOINTMENT_COLUMNS = 5;
    public static long OTP_COUNT_DOWN_TIMER_DEFAULT_VALUE = 10 * 60 * 1000;
    public static long COUNT_DOWN_TIMER_INTERVAL = 1000;
    public static final String OPEN_NEW_FILE = "NEW_FILE";
    public static final String RESET_PASSWORD = "RESET_PASSWORD";
    public static final String LOGIN = "LOGIN";
    public static final String FIND_YOUR_ID = "FIND_YOUR_ID";
    public static final String VERIFY_PHONE_NUMBER = "VERIFY_PHONE";
    public static final String IMAGE_PERMISSION = "IMAGE";
    public static final String STORAGE_PERMISSION = "STORAGE";
    public static final String LOCATION_PERMISSION = "LOCATION";
    public static final String IMAGE_PERMISSION_ADDRESS = "IMAGE_ADDRESS";
    public static final String SMS_PERMISSION = "SMS";
    //public static final String DUMMY_PROFILE_URL = "http://www.ambsw.com/wp-content/uploads/2015/09/avatar-patient-300x300.png";
    public static final String DUMMY_PROFILE_URL = "https://www.rexultihcp.com/sites/g/files/qhldwo536/files/2018-02/patient-sz-3.png";
    public static final String LOGGED_IN = "1";
    public static final String LOGGED_OUT = "0";

    // ACCOUNT INFORMATION
    public static final String TAG_EMAIL = "email";
    public static final String TAG_NAME = "name";
    public static final String TAG_AREA = "area";
    public static final String TAG_GENDER = "gender";
    public static final String TAG_AGE = "age";
    public static final String TAG_PHONE = "phone";
    public static final String DUMMY_PDF_URL = "https://www.tutorialspoint.com/java/java_tutorial.pdf";
    public static final String DUMMY_HOSPITAL_URL = "https://www.rchsp.med.sa/Pages/default.aspx";
    public static final String DUMMY_PAYMENT_URL = getPaymentURL();
    public static final String PDF_EXTENSION = ".pdf";
    public static final String ALARM_RECEIVER_INTENT_TRIGGER_ALARM = "ALARM_RECEIVER_INTENT_TRIGGER_ALARM";
    public static final String ALARM_RECEIVER_INTENT_STOP_ALARM = "ALARM_RECEIVER_INTENT_STOP_ALARM";
    public static final String CONNECT_TO_WIFI = "CONNECTED_TO_WIFI";
    public static final String CONNECT_TO_MOBILE = "CONNECTED_TO_MOBILE";
    public static final String NOT_CONNECT = "NOT_CONNECTED";
    public final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    static final String UNICODE_FORMAT = "UTF8";
    static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    static final String ENCRYPTION_KEY = "KEYBS87654321KEY01112013";
    static final String CHARSET_CODE = "US-ASCII";

    public static final int PHONE_NUMBER_LIMIT = 10;
    public static final String ROYAL_COMMISSION_HOSPITAL_CODE = "00001";


    public enum InputType {
        TEXT, EMAIL, AREA, AGE, GENDER, DOB, PHONE
    }


    private static String getPaymentURL() {

        String url = "<html>\n" +
                "<head>\n" +
                "<!-- INCLUDE SESSION.JS JAVASCRIPT LIBRARY -->\n" +
                "<script src=\"https://test-bsf.mtf.gateway.mastercard.com/form/version/50/merchant/<MERCHANTID>/session.js\"></script>\n" +
                "<!-- APPLY CLICK-JACKING STYLING AND HIDE CONTENTS OF THE PAGE -->\n" +
                "<style id=\"antiClickjack\">body{display:none !important;}</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<!-- CREATE THE HTML FOR THE PAYMENT PAGE -->\n" +
                "\n" +
                "<div>Please enter your payment details:</div>\n" +
                "<div>Card Number: <input type=\"text\" id=\"card-number\" class=\"input-field\" value=\"\" readonly></div>\n" +
                "<div>Expiry Month:<input type=\"text\" id=\"expiry-month\" class=\"input-field\" value=\"\"></div>\n" +
                "<div>Expiry Year:<input type=\"text\" id=\"expiry-year\" class=\"input-field\" value=\"\"></div>\n" +
                "<div>Security Code:<input type=\"text\" id=\"security-code\" class=\"input-field\" value=\"\" readonly></div>\n" +
                "<div><button id=\"payButton\" onclick=\"pay();\">Pay Now</button></div>\n" +
                "\n" +
                "<!-- JAVASCRIPT FRAME-BREAKER CODE TO PROVIDE PROTECTION AGAINST IFRAME CLICK-JACKING -->\n" +
                "<script type=\"text/javascript\">\n" +
                "if (self === top) {\n" +
                "    var antiClickjack = document.getElementById(\"antiClickjack\");\n" +
                "    antiClickjack.parentNode.removeChild(antiClickjack);\n" +
                "} else {\n" +
                "    top.location = self.location;\n" +
                "}\n" +
                "\n" +
                "PaymentSession.configure({\n" +
                "    fields: {\n" +
                "        // ATTACH HOSTED FIELDS TO YOUR PAYMENT PAGE FOR A CREDIT CARD\n" +
                "        card: {\n" +
                "        \tnumber: \"#card-number\",\n" +
                "        \tsecurityCode: \"#security-code\",\n" +
                "        \texpiryMonth: \"#expiry-month\",\n" +
                "        \texpiryYear: \"#expiry-year\"\n" +
                "        }\n" +
                "    },\n" +
                "    //SPECIFY YOUR MITIGATION OPTION HERE\n" +
                "    frameEmbeddingMitigation: [\"javascript\"],\n" +
                "    callbacks: {\n" +
                "        initialized: function(response) {\n" +
                "            // HANDLE INITIALIZATION RESPONSE\n" +
                "        },\n" +
                "        formSessionUpdate: function(response) {\n" +
                "            // HANDLE RESPONSE FOR UPDATE SESSION\n" +
                "            if (response.status) {\n" +
                "                if (\"ok\" == response.status) {\n" +
                "                    console.log(\"Session updated with data: \" + response.session.id);\n" +
                "  \n" +
                "                    //check if the security code was provided by the user\n" +
                "                    if (response.sourceOfFunds.provided.card.securityCode) {\n" +
                "                        console.log(\"Security code was provided.\");\n" +
                "                    }\n" +
                "  \n" +
                "                    //check if the user entered a Mastercard credit card\n" +
                "                    if (response.sourceOfFunds.provided.card.scheme == 'MASTERCARD') {\n" +
                "                        console.log(\"The user entered a Mastercard credit card.\")\n" +
                "                    }\n" +
                "                } else if (\"fields_in_error\" == response.status)  {\n" +
                "  \n" +
                "                    console.log(\"Session update failed with field errors.\");\n" +
                "                    if (response.errors.cardNumber) {\n" +
                "                        console.log(\"Card number invalid or missing.\");\n" +
                "                    }\n" +
                "                    if (response.errors.expiryYear) {\n" +
                "                        console.log(\"Expiry year invalid or missing.\");\n" +
                "                    }\n" +
                "                    if (response.errors.expiryMonth) {\n" +
                "                        console.log(\"Expiry month invalid or missing.\");\n" +
                "                    }\n" +
                "                    if (response.errors.securityCode) {\n" +
                "                        console.log(\"Security code invalid.\");\n" +
                "                    }\n" +
                "                } else if (\"request_timeout\" == response.status)  {\n" +
                "                    console.log(\"Session update failed with request timeout: \" + response.errors.message);\n" +
                "                } else if (\"system_error\" == response.status)  {\n" +
                "                    console.log(\"Session update failed with system error: \" + response.errors.message);\n" +
                "                }\n" +
                "            } else {\n" +
                "                console.log(\"Session update failed: \" + response);\n" +
                "            }\n" +
                "        }\n" +
                "      }\n" +
                "  });\n" +
                "\n" +
                "function pay() {\n" +
                "    // UPDATE THE SESSION WITH THE INPUT FROM HOSTED FIELDS\n" +
                "    PaymentSession.updateSessionFromForm('card');\n" +
                "}\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";

        return url;
    }

}
