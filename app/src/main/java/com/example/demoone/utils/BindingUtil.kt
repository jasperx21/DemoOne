@file:Suppress("DEPRECATION")

package com.example.demoone.utils

import android.os.Build
import android.text.Html
import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingUtil {
  @JvmStatic
  @BindingAdapter("snippetText")
  fun snippetText(
    textView: TextView,
    snippet: String?
  ) {
    textView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
      Html.fromHtml(snippet, Html.FROM_HTML_OPTION_USE_CSS_COLORS)
    else
      Html.fromHtml(snippet)
  }
}