package com.alif.mangaapps.ui.chapter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alif.mangaapps.R
import com.alif.mangaapps.databinding.ActivityChapterBinding
import com.alif.mangaapps.ui.adapter.ChapterAdapter
import com.alif.mangaapps.ui.viewmodel.MangaViewModel
import com.alif.mangaapps.viewmodel.ViewModelFactory

class ChapterActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var activityChapterBinding: ActivityChapterBinding

    @SuppressLint("LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityChapterBinding = ActivityChapterBinding.inflate(layoutInflater)
        setContentView(activityChapterBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[MangaViewModel::class.java]

        val chapterAdapter = ChapterAdapter()

        if(extras != null) {
            val mangaId = extras.getString(EXTRA_ID)

            with(activityChapterBinding.rvChapter) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = chapterAdapter
            }

            viewModel.getChapter(mangaId!!).observe(this, Observer { listChapter ->
                chapterAdapter.setChapter(listChapter!!)
                Log.d("ChapterActivity, mangaid", mangaId)
            })
        }


    }
}