package com.kolisnichenko2828.randomusers.presentation.details

import androidx.compose.runtime.Immutable

@Immutable
data class DetailsUiModel(
    val uuid: String,
    val header: HeaderUiModel,
    val accountInfo: AccountUiModel,
    val contactInfo: ContactInfoUiModel,
    val location: LocationUiModel,
    val workInfo: WorkUiModel,
    val paymentInfo: PaymentUiModel,
    val technicalData: TechnicalDataUiModel
)

@Immutable
data class HeaderUiModel(
    val avatarUrl: String,
    val fullName: String,
    val dob: String,
    val age: Int,
    val gender: String
)

@Immutable
data class AccountUiModel(
    val id: String,
    val username: String,
    val password: String,
    val url: String,
    val domain: String
)

@Immutable
data class ContactInfoUiModel(
    val email: String,
    val phone: String,
    val cell: String
)

@Immutable
data class LocationUiModel(
    val country: String,
    val state: String,
    val city: String,
    val streetAddress: String,
    val postalCode: String,
    val coordinates: String,
    val timezone: String
)

@Immutable
data class WorkUiModel(
    val company: String,
    val jobTitle: String,
    val companyEmail: String
)

@Immutable
data class PaymentUiModel(
    val ssn: String,
    val creditCard: String,
    val iban: String
)

@Immutable
data class TechnicalDataUiModel(
    val uuid: String,
    val ipv4: String,
    val ipv6: String,
    val macAddress: String,
    val userAgent: String,
    val md5: String,
    val sha1: String,
    val sha256: String,
)