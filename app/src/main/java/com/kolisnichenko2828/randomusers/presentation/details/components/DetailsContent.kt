package com.kolisnichenko2828.randomusers.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kolisnichenko2828.randomusers.R
import com.kolisnichenko2828.randomusers.presentation.details.DetailsUiModel

@Composable
fun DetailsContent(
    uiModel: DetailsUiModel
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(400.dp),
        contentPadding = PaddingValues(16.dp),
        verticalItemSpacing = 16.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(span = StaggeredGridItemSpan.FullLine) {
            HeaderSectionCard(
                header = uiModel.header,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            InfoSectionCard(
                title = stringResource(R.string.title_account),
                items = listOf(
                    stringResource(R.string.item_id) to uiModel.accountInfo.id,
                    stringResource(R.string.item_username) to uiModel.accountInfo.username,
                    stringResource(R.string.item_password) to uiModel.accountInfo.password,
                    stringResource(R.string.item_url) to uiModel.accountInfo.url,
                    stringResource(R.string.item_domain) to uiModel.accountInfo.domain,
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            InfoSectionCard(
                title = stringResource(R.string.title_contacts),
                items = listOf(
                    stringResource(R.string.item_email) to uiModel.contactInfo.email,
                    stringResource(R.string.item_phone) to uiModel.contactInfo.phone,
                    stringResource(R.string.item_cell) to uiModel.contactInfo.cell
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            InfoSectionCard(
                title = stringResource(R.string.title_location),
                items = listOf(
                    stringResource(R.string.item_country) to uiModel.location.country,
                    stringResource(R.string.item_state) to uiModel.location.state,
                    stringResource(R.string.item_city) to uiModel.location.city,
                    stringResource(R.string.item_street) to uiModel.location.streetAddress,
                    stringResource(R.string.item_postal_code) to uiModel.location.postalCode,
                    stringResource(R.string.item_coordinates) to uiModel.location.coordinates,
                    stringResource(R.string.item_time_zone) to uiModel.location.timezone
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            InfoSectionCard(
                title = stringResource(R.string.title_work),
                items = listOf(
                    stringResource(R.string.item_company) to uiModel.workInfo.company,
                    stringResource(R.string.item_job_title) to uiModel.workInfo.jobTitle,
                    stringResource(R.string.item_company_email) to uiModel.workInfo.companyEmail
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            InfoSectionCard(
                title = stringResource(R.string.title_payment),
                items = listOf(
                    stringResource(R.string.item_ssn) to uiModel.paymentInfo.ssn,
                    stringResource(R.string.item_credit_card) to uiModel.paymentInfo.creditCard,
                    stringResource(R.string.item_iban) to uiModel.paymentInfo.iban
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            InfoSectionCard(
                title = stringResource(R.string.title_technical),
                items = listOf(
                    stringResource(R.string.item_uuid) to uiModel.technicalData.uuid,
                    stringResource(R.string.item_mac) to uiModel.technicalData.macAddress,
                    stringResource(R.string.item_user_agent) to uiModel.technicalData.userAgent,
                    stringResource(R.string.item_ipv4) to uiModel.technicalData.ipv4,
                    stringResource(R.string.item_ipv6) to uiModel.technicalData.ipv6,
                    stringResource(R.string.item_md5) to uiModel.technicalData.md5,
                    stringResource(R.string.item_sha1) to uiModel.technicalData.sha1,
                    stringResource(R.string.item_sha256) to uiModel.technicalData.sha256,
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}