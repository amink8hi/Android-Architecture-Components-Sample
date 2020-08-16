package ir.yara.batman.ui.view.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.request.CachePolicy
import coil.size.Scale
import coil.transform.RoundedCornersTransformation

import ir.yara.batman.R


@BindingAdapter("listImage")
fun bindImage(imageView: ImageView, url: String?) {
    if (url.isNullOrEmpty()) {
        imageView.load(R.drawable.place_holder) {
            transformations(RoundedCornersTransformation(imageView.resources.getDimension(R.dimen._4sdp)))
        }
    } else {
        imageView.load(url) {
            transformations(RoundedCornersTransformation(imageView.resources.getDimension(R.dimen._4sdp)))
            placeholder(R.drawable.place_holder)
            diskCachePolicy(CachePolicy.ENABLED)
            crossfade(true)
            scale(Scale.FIT)
        }
    }
}