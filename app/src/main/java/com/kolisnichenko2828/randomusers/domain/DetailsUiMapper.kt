package com.kolisnichenko2828.randomusers.domain

import com.kolisnichenko2828.randomusers.presentation.details.AccountUiModel
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

            dob = this.dob,
            age = this.age,

            gender = this.gender.replaceFirstChar { it.uppercase() }
        ),

        accountInfo = AccountUiModel(
            id = this.id,
            username = this.username,
            password = this.password,
            url = this.url,
            domain = this.domain
        ),

        contactInfo = ContactInfoUiModel(
            email = this.email,
            phone = this.phone,
            cell = this.cell
        ),

        location = LocationUiModel(
            country = this.country,
            state = this.state,
            city = this.city,
            streetAddress = this.streetAddress,
            postalCode = this.postalCode,
            coordinates = "${this.latitude}, ${this.longitude}",
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
            ipv4 = this.ipv4,
            ipv6 = this.ipv6,
            macAddress = this.macAddress,
            userAgent = this.userAgent,
            md5 = this.md5,
            sha1 = this.sha1,
            sha256 = this.sha256,
        )
    )
}