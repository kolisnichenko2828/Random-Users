package com.kolisnichenko2828.randomusers.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kolisnichenko2828.randomusers.R
import com.kolisnichenko2828.randomusers.presentation.details.HeaderUiModel

@Composable
fun HeaderSectionCard(
    header: HeaderUiModel,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        if (maxWidth > 400.dp) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                HeaderAvatar(avatarUrl = header.avatarUrl)
                HeaderUserInfo(
                    header = header,
                    horizontalAlignment = Alignment.Start
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                HeaderAvatar(avatarUrl = header.avatarUrl)
                HeaderUserInfo(
                    header = header,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
            }
        }
    }
}

@Composable
private fun HeaderAvatar(
    avatarUrl: String
) {
    AsyncImage(
        model = avatarUrl,
        contentDescription = stringResource(R.string.user_avatar),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(Color.Gray)
    )
}

@Composable
private fun HeaderUserInfo(
    header: HeaderUiModel,
    horizontalAlignment: Alignment.Horizontal
) {
    Column(
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = header.fullName,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${header.gender}, ${header.dob} (${header.age})",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}