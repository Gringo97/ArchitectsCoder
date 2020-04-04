package oscar.delafuente.arichitectcoders.ui

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import oscar.delafuente.arichitectcoders.ui.common.loadUrl

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (url != null) loadUrl(url)
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = visible?.let {
        if (visible) View.VISIBLE else View.GONE
    } ?: View.GONE
}