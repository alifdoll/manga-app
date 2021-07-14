package com.alif.mangaapps.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alif.mangaapps.R
import com.alif.mangaapps.databinding.ActivityMangaDetailBinding
import com.alif.mangaapps.databinding.ContentDetailBinding
import com.alif.mangaapps.ui.viewmodel.MangaViewModel
import com.alif.mangaapps.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MangaDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var contentDetailBinding: ContentDetailBinding

    @SuppressLint("LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailBinding = ActivityMangaDetailBinding.inflate(layoutInflater)
        contentDetailBinding = activityDetailBinding.detailContent

        setContentView(activityDetailBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[MangaViewModel::class.java]

        if(extras != null) {
            val mangaId = extras.getString(EXTRA_ID)
            if(mangaId != null) {
                viewModel.getMangaDetail(mangaId).observe(this, Observer { manga ->
                    Log.d("Debug detailActivity viewmodel", manga.title)
                    Log.d("Debug detailActivity viewmodel", manga.coverArt)
                    contentDetailBinding.detailTitle.text = manga.title
                    contentDetailBinding.detailDescription.text = manga.desc

                    if(manga.coverArt == "") {
                        Log.d("Debug detailActivity viewmodel", "cover null")
                    } else {
                        Log.d("Debug detailActivity viewmodel", "cover ada")
                    }
                    Glide.with(this)
                        .load(manga.coverArt)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_load)
                            .error(R.drawable.ic_error))
                        .into(contentDetailBinding.detailPoster)

                    Glide.with(this)
                        .load(manga.coverArt)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_load)
                            .error(R.drawable.ic_error))
                        .into(activityDetailBinding.detailPosterBg)


                })
            } else {
                Log.d("Debug detailActivity viewmodel", "error")
            }
        }
    }
}