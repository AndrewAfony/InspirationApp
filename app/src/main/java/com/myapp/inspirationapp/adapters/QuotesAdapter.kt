package com.myapp.inspirationapp.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myapp.inspirationapp.R
import com.myapp.inspirationapp.databinding.ItemQuoteBinding
import com.myapp.inspirationapp.domain.model.Quote
import com.myapp.inspirationapp.utils.toCategory

class QuotesAdapter: RecyclerView.Adapter<QuotesAdapter.QuoteViewHolder>() {

    inner class QuoteViewHolder(val binding: ItemQuoteBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object :DiffUtil.ItemCallback<Quote>() {

        override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        return QuoteViewHolder(
            ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = differ.currentList[position]
        holder.binding.apply {
            quoteText.text = quote.content
            author.text = quote.author
            tag.text = quote.tags[0].toCategory()

            root.setOnLongClickListener {
                showMenu(this.root.context, it)
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}

private fun showMenu(context: Context, view: View) {

    PopupMenu(context, view).apply {

        setOnMenuItemClickListener {
            return@setOnMenuItemClickListener when (it.itemId) {
                R.id.save_to_favorite -> {
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.share -> {
                    true
                }
                else -> false
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setForceShowIcon(true)
        }

        inflate(R.menu.quote_menu)
        show()

    }
}
