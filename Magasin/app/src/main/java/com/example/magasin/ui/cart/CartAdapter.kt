package com.example.magasin.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.magasin.databinding.CartItemBinding
import com.example.magasin.databinding.ShopItemBinding
import com.example.magasin.model.ShopItem



class CartAdapter(private var cartItems: List<ShopItem>) :RecyclerView.Adapter<CartAdapter.ViewHolder>()
{

    inner class ViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(shopItem: ShopItem) {
            binding.textItem.text = shopItem.name
            binding.textQuantity.text = shopItem.quantity.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shopItem = cartItems[position]
        holder.bind(shopItem)
    }

    override fun getItemCount(): Int = cartItems.size
}