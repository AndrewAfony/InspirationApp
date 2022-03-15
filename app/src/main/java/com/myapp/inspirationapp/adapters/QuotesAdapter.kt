package com.myapp.inspirationapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myapp.inspirationapp.databinding.FavoriteItemQuoteBinding
import com.myapp.inspirationapp.databinding.ItemQuoteBinding
import com.myapp.inspirationapp.domain.model.Quote
import com.myapp.inspirationapp.utils.toCategory

private const val QUOTE = 0
private const val FAVORITE_QUOTE = 1

class QuotesAdapter(
    private val isFavorite: Boolean = false,
    private val onClick: (View, Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class QuoteViewHolder(val binding: ItemQuoteBinding): RecyclerView.ViewHolder(binding.root)

    inner class FavoriteQuoteViewHolder(val binding: FavoriteItemQuoteBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object :DiffUtil.ItemCallback<Quote>() {

        override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemViewType(position: Int): Int {
        return if(!isFavorite) {
            QUOTE
        } else {
            FAVORITE_QUOTE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(viewType) {
            QUOTE -> {
                return QuoteViewHolder(
                    ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            FAVORITE_QUOTE -> {
                return FavoriteQuoteViewHolder(
                    FavoriteItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> {
                return QuoteViewHolder(
                    ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val quote = differ.currentList[position]
        when (holder.itemViewType) {
            QUOTE -> {
                (holder as QuoteViewHolder).binding.apply {
                    quoteText.text = quote.content
                    author.text = quote.author
                    tag.text = quote.tags[0].toCategory()

                    root.setOnLongClickListener {
                        onClick(it, position)
                        true
                    }
                }
            }
            FAVORITE_QUOTE -> {
                (holder as FavoriteQuoteViewHolder).binding.apply {
                    quoteText.text = quote.content
                    author.text = quote.author
                    tag.text = quote.tags[0].toCategory()
                }

            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}



