package com.kentlogic.mvvm_template.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.kentlogic.mvvm_template.R
import com.kentlogic.mvvm_template.databinding.ActivityMainBinding
import com.kentlogic.mvvm_template.model.Photos
import com.kentlogic.mvvm_template.viewmodel.PhotosViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PhotosViewModel
    private val photoListAdapter = PhotoListAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Connect the adapter to the recyclerview
        rv_images.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = photoListAdapter
        }

        viewModel = ViewModelProviders.of(this).get(PhotosViewModel::class.java)
        viewModel.refresh()

        //reload the data
        swipeView.setOnRefreshListener {
            viewModel.refresh()
            swipeView.isRefreshing = false
        }

        //Observe changes to the viewmodel
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.photos.observe(this, Observer { photos ->
            photos?.let {
                if (!photos.isNullOrEmpty()) {
                    photoListAdapter.updatePhotoList(photos)
                    rv_images.visibility = View.VISIBLE
                }
            }
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            isLoading?.let {
                pb_photos.visibility = if(isLoading) View.VISIBLE else View.GONE
            }
        })

        viewModel.hasError.observe(this, Observer { error ->
            error?.let { 
                tv_error.visibility = if(error) View.VISIBLE else View.GONE

                if(error) {
                    rv_images.visibility = View.GONE
                }
            }
        })
    }
}