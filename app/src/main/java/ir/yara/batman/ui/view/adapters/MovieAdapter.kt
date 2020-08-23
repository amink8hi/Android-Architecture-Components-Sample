package ir.yara.batman.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import ir.yara.batman.R
import ir.yara.batman.data.remote.responce.batmanlist.SearchModel
import ir.yara.batman.databinding.ItemMovieBinding
import ir.yara.batman.ui.viewmodel.MovieItemVM


class MovieAdapter(private val list: MutableList<SearchModel?>?) :
    RecyclerView.Adapter<MovieAdapter.MainViewHolder>() {

    private lateinit var binding: ItemMovieBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        binding = holder.itemMainReportsBinding
        val viewModel = MovieItemVM(list!![position], binding.itemLayoutMovie)
        binding.vm = viewModel
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onViewAttachedToWindow(holder: MainViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.markAttach()
    }

    override fun onViewDetachedFromWindow(holder: MainViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.markDetach()
    }

    inner class MainViewHolder(var itemMainReportsBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemMainReportsBinding.root), LifecycleOwner {
        private val lifecycleRegistry = LifecycleRegistry(this)

        init {
            lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
        }

        override fun getLifecycle(): Lifecycle {
            return lifecycleRegistry
        }

        fun markAttach() {
            lifecycleRegistry.currentState = Lifecycle.State.STARTED
        }

        fun markDetach() {
            lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        }
    }

}