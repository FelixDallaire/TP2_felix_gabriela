package com.example.magasin.ui.shop


import MainViewModel
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.magasin.databinding.ShopItemBinding
import com.example.magasin.model.ShopItem

class ShopAdapter(internal var shopItems: MutableList<ShopItem>,private val fragment: Fragment,private var mainViewModel: MainViewModel ) :
    RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

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

            binding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->
                val position = adapterPosition


                val edit: android.view.MenuItem = menu.add(0, v.id, 0, "Edit")
                val delete: android.view.MenuItem = menu.add(0, v.id, 0, "Delete")
                edit.setOnMenuItemClickListener {
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onClickEdit(itemView, position)
                    }
                    false
                }
                delete.setOnMenuItemClickListener {
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onClickDelete(position)
                    }
                    false
                }
                mainViewModel = ViewModelProvider(fragment).get(MainViewModel::class.java)


                mainViewModel.isAdmin.observe(fragment.viewLifecycleOwner) { isAdmin ->
                    edit.isVisible = isAdmin


                    Log.d("AdminMode","isAdmin: "+ isAdmin.toString())
                }

            }
        }

        fun bind(shopItem: ShopItem) {
            binding.tvProductName.text = shopItem.name
            binding.tvProductDescription.text = shopItem.description
            binding.tvProductPrice.text = String.format("%.2f", shopItem.price)
            // TODO: Add image handling if needed
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
