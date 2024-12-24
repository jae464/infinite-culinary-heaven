package com.jae464.presentation.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jae464.domain.model.Comment
import com.jae464.presentation.ui.theme.Gray20
import com.jae464.presentation.util.ImageConstants
import java.time.format.DateTimeFormatter

@Composable
fun CommentItem(comment: Comment, isOwner: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = comment.userInfo.profileImageUrl ?: ImageConstants.DEFAULT_PROFILE_IMAGE_URL,
            contentDescription = null,
            modifier = Modifier.size(32.dp).clip(CircleShape)
        )
        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = comment.userInfo.name,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = comment.content,
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp).fillMaxWidth(),
            ) {
                Text(
                    text = comment.createdAt.format(
                        DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")),
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                if (isOwner){
                    Row {
                        Text(
                            text = "수정",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "삭제",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}