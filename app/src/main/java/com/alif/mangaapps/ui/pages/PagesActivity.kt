package com.alif.mangaapps.ui.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alif.mangaapps.R
import com.alif.mangaapps.databinding.ActivityPagesBinding
import com.alif.mangaapps.ui.adapter.PageAdapter
import com.alif.mangaapps.ui.viewmodel.MangaViewModel
import com.alif.mangaapps.viewmodel.ViewModelFactory

class PagesActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var activityPagesBinding: ActivityPagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityPagesBinding = ActivityPagesBinding.inflate(layoutInflater)
        setContentView(activityPagesBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[MangaViewModel::class.java]

        val pageAdapter = PageAdapter()

        if(extras != null) {
            val chapterId = extras.getString(EXTRA_ID)

            with(activityPagesBinding.rvPage) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = pageAdapter
            }

            viewModel.getChapterPages(chapterId!!).observe(this, Observer { listPage ->
                pageAdapter.setPage(listPage!!)
            })
        }
    }
}