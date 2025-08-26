package com.vaasudev.composesigninsignup.domain.ktor

class KtorUtility {

    object Client {
        const val HTTP_CLIENT_TIMEOUT = 60_000
    }

    object HeaderKey {
        const val X_API_KEY = "x-api-key"
        const val LANG = "lang"
    }

    object MiddlePoint {
        const val AUTH = "api/v1/"
    }

    object EndPoint {
        //const val GET_EMPLOYEE_DETAILS = MiddlePoint.AUTH.plus("employee/1")
        const val GET_EMPLOYEE_DETAILS = "https://dummy.restapiexample.com/api/v1/employee/1"
        const val CREATE_EMPLOYEE = "https://dummy.restapiexample.com/api/v1/create"
        const val UPLOAD_DOC = "https://dev.shoppinggate.app/api/v5/customer/Onevisa/upload_document"
        const val CREATE_PAYMENT_INTENTS = "https://api.stripe.com/v1/payment_intents"

    }

    object ParamValue {
        const val USER_ID = "user_id"
        const val ORDER_ID = "order_id"
        const val APPLICATION_ID = "application_id"
        const val ORDER_NUMBER = "order_number"
        const val DOCUMENT_CODE = "document_code"
        const val FILE = "file"
        const val NAME = "name"
        const val SALARY = "salary"
        const val AGE = "age"
        const val AMOUNT = "amount"
        const val CURRENCY = "currency"
        const val AUTOMATIC_PAYMENT_METHODS = "automatic_payment_methods[enabled]"
    }
}