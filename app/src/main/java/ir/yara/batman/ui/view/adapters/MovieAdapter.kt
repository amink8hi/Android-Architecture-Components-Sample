package ir.yara.batman.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
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
        val viewModel = MovieItemVM(list!![position])
        binding.vm = viewModel

        //marquee
        binding.title.isSelected = true


        val bundle = bundleOf(
            "imdbID" to viewModel.movieListModel?.imdbID
        )
        binding.itemLayoutMovie.setOnClickListener(View.OnClickListener {
            it.findNavController().navigate(R.id.action_movieFragment_to_DetailFragment, bundle)
        })

    }

    override fun getItemCount(): Int {
        return list?.size!!
    }


    inner class MainViewHolder(var itemMainReportsBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemMainReportsBinding.root)

}