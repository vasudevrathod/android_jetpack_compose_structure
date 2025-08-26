package com.vaasudev.androidcomposestructure.domain.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailsResponse(
    var status: String = "",
    var message: String = "",
    val data: UserDetailsDataResponse
)

@Serializable
data class UserDetailsDataResponse(
    val id: Int = 0,

    @SerialName("employee_name")
    val employeeName: String = "",

    @SerialName("employee_salary")
    val employeeSalary: String = "",

    @SerialName("employee_age")
    val employeeAge: String = "",

    @SerialName("profile_image")
    val profileImage: String = ""
)
