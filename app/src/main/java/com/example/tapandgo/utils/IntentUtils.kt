package com.example.tapandgo.utils

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity


fun getSendSupportEmailIntent(): Intent {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:") // only email apps should handle this

    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("podpora@jozko-mrkvicka.sk"))
    intent.putExtra(Intent.EXTRA_SUBJECT, "Tap And Go Question")
    return intent
}