package com.example.febtestproject.presentation.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.setViewTreeOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.example.febtestproject.R

class CustomAlert<T>(
    private val activity: T,
) : Dialog(activity) where T : Context, T : androidx.lifecycle.LifecycleOwner, T : ViewModelStoreOwner, T : SavedStateRegistryOwner, T : OnBackPressedDispatcherOwner {

    private fun initViewTreeOwners() {
        val window = window ?: return
        ViewTreeLifecycleOwner.set(window.decorView, activity)
        ViewTreeViewModelStoreOwner.set(window.decorView, activity)
        window.decorView.setViewTreeSavedStateRegistryOwner(activity)
        window.decorView.setViewTreeOnBackPressedDispatcherOwner(activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewTreeOwners()
        setCancelable(false)
        setContentView(R.layout.dialog_alert)
        val circles = findViewById<ComposeView>(R.id.cv_circles)
        circles.setContent {
            DrawCircles()
        }
        window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        val btnClose = findViewById<Button>(R.id.btn_close_dialog)
        btnClose.setOnClickListener {
            dismiss()
        }
    }
}

@Preview
@Composable
fun DrawCircles() {
    Circles()
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
    )
}

@Composable
fun Circles() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .offset(y = 34.dp)
            .padding(horizontal = 52.dp)
            .background(Color(0xFF313841))
    )
    Row(
        modifier = Modifier
            .padding(
                top = 25.dp, bottom = 48.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Circle(
            borderColor = Color(0xFF055AFF),
            ballColor = Color(0xFF055AFF),
            s = "Fist",
            false
        )
        Circle(
            borderColor = Color(0xFFD9D9D9),
            ballColor = Color(0xFFD900D0),
            s = "Secont",
            false
        )
        Circle(
            borderColor = Color(0xFFD900D0),
            ballColor = Color(0xFFD9D9D9),
            s = "Third",
            true
        )
        Circle(
            borderColor = Color(0xFF9E00FF),
            ballColor = Color(0xFFD9D9D9),
            s = "Fourth",
            true
        )
    }
}

@Composable
fun Circle(
    borderColor: Color, ballColor: Color, s: String, isShadowEqualsBorder: Boolean
) {
    val shadowColor = if (isShadowEqualsBorder) borderColor
    else ballColor
    val spotFontFamily = FontFamily(
        Font(R.font.spot_light, FontWeight.Light),
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            shadowColor, Color.Transparent
                        )
                    )
                )
                .size(18.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(ballColor)
                    .border(1.dp, borderColor, CircleShape)
                    .size(12.dp),
            )
        }
        Text(
            text = s,
            fontSize = 10.sp,
            modifier = Modifier.padding(top = 5.dp),
            fontFamily = spotFontFamily,
            fontWeight = FontWeight.Light
        )
    }
}
