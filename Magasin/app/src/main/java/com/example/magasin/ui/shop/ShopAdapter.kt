package com.example.magasin.ui.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.magasin.R
import com.example.magasin.databinding.ShopItemBinding
import com.example.magasin.model.ShopItem
import com.example.magasin.utils.CategoryImageUtils

class ShopAdapter(internal var shopItems: MutableList<ShopItem>, private var isAdminMode: Boolean = false) :
    RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    fun updateAdminMode(isAdmin: Boolean) {
        isAdminMode = isAdmin
        notifyDataSetChanged()
    }

    interface OnItemClickListenerInterface {
        fun onItemClick(itemView: View?, position: Int)
        fun onClickEdit(itemView: View, position: Int)
        fun onClickDelete(position: Int)
    }

    lateinit var listener: OnItemClickListenerInterface

    fun setOnItemClickListener(listener: OnItemClickListenerInterface) {
        this.listener = listener
    }

    fun updateItems(newItems: List<ShopItem>) {
        shopItems.clear()
        shopItems.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ShopItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, adapterPosition)
                }
            }

            setupContextMenu()
        }

        private fun setupContextMenu() {
            binding.root.setOnCreateContextMenuListener { menu, v, _ ->
                if (isAdminMode) {
                    val position = adapterPosition
                    createMenuItem(menu, v.id, R.string.action_edit) {
                        listener.onClickEdit(itemView, position)
                    }
                    createMenuItem(menu, v.id, R.string.action_delete) {
                        listener.onClickDelete(position)
                    }
                }
            }
        }

        private fun createMenuItem(menu: android.view.ContextMenu, groupId: Int, titleResId: Int, action: () -> Unit) {
            val menuItem = menu.add(0, groupId, 0, titleResId)
            menuItem.setOnMenuItemClickListener {
                action()
                false
            }
        }

        fun bind(shopItem: ShopItem) {
            binding.tvProductName.text = shopItem.name
            binding.tvProductDescription.text = shopItem.description
            binding.tvProductPrice.text = String.format("%.2f", shopItem.price)

            val imageResId = CategoryImageUtils.getImageResIdForCategory(shopItem.category)
            binding.ivProductImage.setImageResource(imageResId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ShopItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shopItem = shopItems[position]
        holder.bind(shopItem)
    }

    override fun getItemCount(): Int = shopItems.size
}
