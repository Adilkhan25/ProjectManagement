package com.adilkhan.projemanage.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adilkhan.projemanage.R
import com.adilkhan.projemanage.databinding.ItemBoardBinding
import com.adilkhan.projemanage.models.Board
import com.bumptech.glide.Glide

open class BoardItemsAdapter(private val context:Context,
                             private val list : ArrayList<Board>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MyViewHolder(private val itemBoardBinding: ItemBoardBinding):RecyclerView.ViewHolder(itemBoardBinding.root)
    {
        fun bindItem(model:Board)
        {
            Glide
                .with(context)
                .load(model.image)
                .centerCrop()
                .placeholder(R.drawable.ic_board_place_holder)
                .into(itemBoardBinding.ivBoardImage)
            itemBoardBinding.tvName.text = model.name
            itemBoardBinding.tvCreatedBy.text = "Created By : ${model.createdBy}"
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            ItemBoardBinding.inflate(
            LayoutInflater.from(context),
            parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val model = list[position]
            if(holder is MyViewHolder)
            {
                holder.bindItem(model)

            }
    }

    override fun getItemCount(): Int {
       return list.size
    }

}