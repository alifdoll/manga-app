package com.alif.mangaapps.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.alif.mangaapps.R
import com.alif.mangaapps.databinding.ItemPageBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class PageAdapter : RecyclerView.Adapter<PageAdapter.PageViewHolder>() {

    private val listPage = ArrayList<String>()

    fun setPage(pages: List<String>?) {
        if(pages == null) return
        listPage.clear()
        listPage.addAll(pages)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val itemsBinding = ItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PageViewHolder(itemsBinding)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val page = listPage[position]
        holder.bind(page)
    }

    override fun getItemCount(): Int = listPage.size

    inner class PageViewHolder(private val itemsBinding: ItemPageBinding) : RecyclerView.ViewHolder(itemsBinding.root) {
        fun bind(page: String) {
            with(itemsBinding) {
                Glide.with(itemView.context)
                    .load(page)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_load)
                        .error(R.drawable.ic_error))
                    .into(itemPageImage)
            }
        }

    }
}