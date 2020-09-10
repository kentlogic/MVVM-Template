package com.kentlogic.mvvm_template.view

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import com.kentlogic.mvvm_template.databinding.ActivityMainBinding
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kentlogic.mvvm_template.R
import com.kentlogic.mvvm_template.databinding.ItemPhotoBinding
import com.kentlogic.mvvm_template.model.Photos
//class PhotoListAdapter{}
class PhotoListAdapter(val photoList: ArrayList<Photos>): RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>(){

    //For updating the list
    fun updatePhotoList(newPhotoList: List<Photos>) {
        photoList.clear()
        photoList.addAll(newPhotoList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemPhotoBinding>(inflater, R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    //bind the data to the view
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.view.photos = photoList[position]
    }

    override fun getItemCount(): Int = photoList.size

    class PhotoViewHolder (var view: ItemPhotoBinding): RecyclerView.ViewHolder(view.root)
}