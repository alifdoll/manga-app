package com.alif.mangaapps.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.alif.mangaapps.data.entity.ChapterEntity
import com.alif.mangaapps.databinding.ItemChapterBinding

class ChapterAdapter : RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>() {

    private  val listChapter = ArrayList<ChapterEntity>()

    fun setChapter(chapters: List<ChapterEntity>?) {
        if(chapters == null) return
        Log.d("ChapterAdapter, size", chapters.size.toString())
        listChapter.clear()
        listChapter.addAll(chapters)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterAdapter.ChapterViewHolder {
        val itemsBinding = ItemChapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChapterViewHolder(itemsBinding)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        val chapter = listChapter[position]
        holder.bind(chapter)
    }

    override fun getItemCount(): Int = listChapter.size

    inner class ChapterViewHolder(private val binding: ItemChapterBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("LongLogTag")
        fun bind(chapter: ChapterEntity) {
            with(binding) {
                chapterNumber.text = chapter.number
                chapterTitle.text = chapter.title

                Log.d("ChapterAdapter, number title", "${chapter.number}, ${chapter.title}")
            }
        }

    }
}