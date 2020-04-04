package oscar.delafuente.arichitectcoders.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import oscar.delafuente.arichitectcoders.ui.common.loadUrl

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (url != null) loadUrl(url)
}