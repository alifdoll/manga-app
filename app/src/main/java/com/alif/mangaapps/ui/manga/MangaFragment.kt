package com.alif.mangaapps.ui.manga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alif.mangaapps.R
import com.alif.mangaapps.databinding.FragmentMangaBinding
import com.alif.mangaapps.ui.adapter.MangaAdapter
import com.alif.mangaapps.ui.viewmodel.MangaViewModel
import com.alif.mangaapps.viewmodel.ViewModelFactory

class MangaFragment : Fragment() {

    private lateinit var fragmentMangaBinding: FragmentMangaBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMangaBinding = FragmentMangaBinding.inflate(layoutInflater, container, false)
        return fragmentMangaBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[MangaViewModel::class.java]

            val mangaAdapter = MangaAdapter()

            with(fragmentMangaBinding.rvManga) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = mangaAdapter
            }

            viewModel.getManga().observe(viewLifecycleOwner, Observer { mangas ->
                fragmentMangaBinding.progressBar.visibility = View.GONE
                mangaAdapter.setManga(mangas!!)
            })
        }
    }

}