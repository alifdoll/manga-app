package com.alif.mangaapps.ui.adapter

import android.content.Intent
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alif.mangaapps.R
import com.alif.mangaapps.data.entity.MangaEntity
import com.alif.mangaapps.databinding.ItemBinding
import com.alif.mangaapps.ui.detail.MangaDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MangaAdapter : RecyclerView.Adapter<MangaAdapter.MangaViewHolder>() {

    private var listManga = ArrayList<MangaEntity>()

    fun setManga(mangas: List<MangaEntity>?) {
        if(mangas == null) return
        listManga.clear()
        listManga.addAll(mangas)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MangaAdapter.MangaViewHolder {
        val itemsMangaBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MangaViewHolder(itemsMangaBinding)
    }

    override fun onBindViewHolder(holder: MangaAdapter.MangaViewHolder, position: Int) {
        val manga = listManga[position]
        holder.bind(manga)
    }

    override fun getItemCount(): Int = listManga.size

    inner class MangaViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(manga: MangaEntity) {
            with(binding) {
                itemTitle.text = manga.title
                itemDescription.text = manga.desc

                Log.d("Manga cover, ", manga.coverArt)

                Glide.with(itemView.context)
                    .load(manga.coverArt)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_load)
                        .error(R.drawable.ic_error))
                    .into(itemCover)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, MangaDetailActivity::class.java)
                    intent.putExtra(MangaDetailActivity.EXTRA_ID, manga.id)
                    itemView.context.startActivity(intent)
                }
            }

        }

    }

}