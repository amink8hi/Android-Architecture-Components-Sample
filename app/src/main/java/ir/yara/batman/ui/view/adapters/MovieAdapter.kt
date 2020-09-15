package ir.yara.batman.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.Nullable
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ir.yara.batman.R
import ir.yara.batman.data.remote.responce.batmanlist.SearchModel
import ir.yara.batman.databinding.ItemMovieBinding
import ir.yara.batman.ui.viewmodel.MovieItemVM


class MovieAdapter(private val list: MutableList<SearchModel>, private val fragment: Fragment?) :
    RecyclerView.Adapter<MovieAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_movie,
            FrameLayout(parent.context), false
        )
        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val dataModel: SearchModel = list[position]
        holder.setViewModel(MovieItemVM(dataModel))


        val bundle = bundleOf("imdbID" to dataModel.imdbID)
        holder.binding?.itemLayoutMovie?.setOnClickListener(View.OnClickListener {
            fragment?.findNavController()
                ?.navigate(R.id.action_movieFragment_to_DetailFragment, bundle)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onViewAttachedToWindow(holder: DataViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.bind()
    }

    override fun onViewDetachedFromWindow(holder: DataViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.unbind()
    }

    fun updateData(@Nullable data: List<SearchModel>?) {
        if (data.isNullOrEmpty()) {
            this.list.addAll(data!!)
        }
        notifyDataSetChanged()
    }

    class DataViewHolder(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ) {

        var binding: ItemMovieBinding? = null

        fun bind() {
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView)
            }
        }

        fun unbind() {
            binding?.unbind()
        }

        fun setViewModel(viewModel: MovieItemVM?) {
            if (binding != null) {
                binding!!.vm = viewModel
            }
        }

        init {
            bind()
        }
    }


}