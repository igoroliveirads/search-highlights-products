package com.example.searchhighlightsproducts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ItemAdapter(
    private val itemList: MutableList<ItemModel>,
    val itemSelected: (ItemModel) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.titleItem.text = item.name
        holder.priceItem.text = item.price.toString()
        Picasso.get().load(item.image).into(holder.imageItem)
        holder.itemView.setOnClickListener {
            itemSelected(item)
        }
    }

    override fun getItemCount(): Int = itemList.size

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleItem: TextView = itemView.findViewById(R.id.text_title_item)
        val priceItem: TextView = itemView.findViewById(R.id.text_price_item)
        val imageItem: ImageView = itemView.findViewById(R.id.image_thumb)
    }
}





