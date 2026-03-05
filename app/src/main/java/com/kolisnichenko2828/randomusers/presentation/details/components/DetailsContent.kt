package com.kolisnichenko2828.randomusers.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kolisnichenko2828.randomusers.presentation.details.DetailsUiModel

@Composable
fun DetailsContent(uiModel: DetailsUiModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderSectionCard(uiModel.header)

        InfoSectionCard(
            title = "Contacts",
            items = listOf(
                "Email" to uiModel.contactInfo.email,
                "Phone 1" to uiModel.contactInfo.phone,
                "Phone 2" to uiModel.contactInfo.cell
            )
        )

        InfoSectionCard(
            title = "Location",
            items = listOf(
                "Address" to uiModel.location.fullAddress,
                "Coordinates" to uiModel.location.coordinates,
                "Time Zone" to uiModel.location.timezone
            )
        )

        InfoSectionCard(
            title = "Work",
            items = listOf(
                "Company" to uiModel.workInfo.company,
                "Job Title" to uiModel.workInfo.jobTitle,
                "Company Email" to uiModel.workInfo.companyEmail
            )
        )

        InfoSectionCard(
            title = "Payment",
            items = listOf(
                "SSN" to uiModel.paymentInfo.ssn,
                "Credit card" to uiModel.paymentInfo.creditCard,
                "IBAN" to uiModel.paymentInfo.iban
            )
        )

        InfoSectionCard(
            title = "Technical",
            items = listOf(
                "UUID" to uiModel.technicalData.uuid,
                "MAC" to uiModel.technicalData.macAddress,
                "Hashes" to uiModel.technicalData.hashes,
                "User Agent" to uiModel.technicalData.userAgent,
                "IP" to uiModel.technicalData.ipAddress
            )
        )
    }
}