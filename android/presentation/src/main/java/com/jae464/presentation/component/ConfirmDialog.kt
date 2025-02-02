package com.jae464.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.jae464.presentation.ui.theme.Gray20
import com.jae464.presentation.ui.theme.Green10

@Composable
fun ConfirmDialog(
    title: String,
    content: String,
    modifier: Modifier = Modifier,
    cancelText: String,
    confirmText: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = modifier.width(350.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    text = title
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = content)
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onCancel,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Gray20,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(text = cancelText)
                    }

                    Button(
                        onClick = onConfirm,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green10,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(text = confirmText)
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun ConfirmDialogPreview() {
    ConfirmDialog(
        title = "정말 삭제할까요?",
        content = "삭제되면 되돌릴 수 없어요",
        confirmText = "확인",
        onDismissRequest = {},
        onConfirm = {},
        onCancel = {},
        cancelText = "취소",
    )
}