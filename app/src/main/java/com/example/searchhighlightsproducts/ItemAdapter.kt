package com.example.searchhighlightsproducts

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ItemAdapter(
    private val itemList: List<ItemEntity>,
    val itemSelected: (ItemEntity) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ItemViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        val amount = item.body.price.div(6)
        holder.titleItem.text = item.body.title
        holder.priceItem.text = "R$ ${item.body.price}"
        holder.installmentAmount.text = "6 x R$ ${amount.toInt()} sem juros"
        Picasso.get().load(item.body.secure_thumbnail).into(holder.imageItem)
        holder.itemView.setOnClickListener {
            itemSelected(item)
        }
    }

    override fun getItemCount(): Int = itemList.size

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleItem: TextView = itemView.findViewById(R.id.text_title_item)
        val priceItem: TextView = itemView.findViewById(R.id.text_price_item)
        val imageItem: ImageView = itemView.findViewById(R.id.image_thumb)
        val installmentAmount: TextView = itemView.findViewById(R.id.text_installment_amount)
    }
}





