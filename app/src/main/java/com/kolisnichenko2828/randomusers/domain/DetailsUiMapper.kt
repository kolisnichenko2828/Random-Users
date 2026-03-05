package com.kolisnichenko2828.randomusers.domain

import com.kolisnichenko2828.randomusers.presentation.details.ContactInfoUiModel
import com.kolisnichenko2828.randomusers.presentation.details.DetailsUiModel
import com.kolisnichenko2828.randomusers.presentation.details.HeaderUiModel
import com.kolisnichenko2828.randomusers.presentation.details.LocationUiModel
import com.kolisnichenko2828.randomusers.presentation.details.PaymentUiModel
import com.kolisnichenko2828.randomusers.presentation.details.TechnicalDataUiModel
import com.kolisnichenko2828.randomusers.presentation.details.WorkUiModel

fun UsersModel.toDetailsUiModel(): DetailsUiModel {
    return DetailsUiModel(
        uuid = this.uuid,
        header = HeaderUiModel(
            avatarUrl = this.picture,

            fullName = listOf(this.prefix, this.firstName, this.lastName, this.suffix)
                .filter { it.isNotBlank() }
                .joinToString(" "),

            dobAndAge = "${this.dob} (${this.age} old)",

            gender = this.gender.replaceFirstChar { it.uppercase() }
        ),

        contactInfo = ContactInfoUiModel(
            email = this.email,
            phone = this.phone,
            cell = this.cell
        ),

        location = LocationUiModel(
            fullAddress = "${this.streetAddress}, ${this.city}, ${this.state} ${this.postalCode}, ${this.country}",
            coordinates = "Latitude: ${this.latitude}, Longitude: ${this.longitude}",
            timezone = this.timezone
        ),

        workInfo = WorkUiModel(
            company = this.company,
            jobTitle = this.job,
            companyEmail = this.companyEmail
        ),

        paymentInfo = PaymentUiModel(
            ssn = this.ssn,

            creditCard = this.creditCard,
            iban = this.iban
        ),

        technicalData = TechnicalDataUiModel(
            uuid = this.uuid,
            ipAddress = "IPv4: ${this.ipv4}\nIPv6: ${this.ipv6}",
            macAddress = this.macAddress,
            userAgent = this.userAgent,
            hashes = "MD5: ${this.md5}\nSHA1: ${this.sha1}\nSHA256: ${this.sha256}"
        )
    )
}