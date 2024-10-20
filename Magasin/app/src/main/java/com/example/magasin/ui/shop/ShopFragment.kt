package com.example.magasin.ui.shop

import MainViewModel
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.magasin.R
import com.example.magasin.databinding.FragmentShopBinding
import com.example.magasin.model.ShopItem
import com.example.magasin.data.MagasinDatabase
import com.example.magasin.data.ShopItemDao

class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    private lateinit var shopViewModel: ShopViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var shopAdapter: ShopAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)

        val database = MagasinDatabase.getInstance(requireContext())
        val shopItemDao = database.shopItemDao()
        shopViewModel =
            ViewModelProvider(this, ViewModelFactory(shopItemDao))[ShopViewModel::class.java]

        setupRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        mainViewModel.isAdminMode.observe(viewLifecycleOwner) { isAdmin ->
            binding.floatingActionButton.visibility = if (isAdmin) View.VISIBLE else View.GONE
            shopAdapter.updateAdminMode(isAdmin)
        }

        shopViewModel.shopItems.observe(viewLifecycleOwner) { items ->
            shopAdapter.updateItems(items)
        }

        binding.floatingActionButton.setOnClickListener {
            showAddEditItemDialog()
        }
    }

    private fun setupRecyclerView() {
        shopAdapter = ShopAdapter(mutableListOf(), false).apply {
            setOnItemClickListener(object : ShopAdapter.OnItemClickListenerInterface {
                override fun onItemClick(itemView: View?, position: Int) {
                    val item = shopItems[position]
                    mainViewModel.addToCart(item)
                }

                override fun onClickEdit(itemView: View, position: Int) {
                    val item = shopItems[position]
                    showAddEditItemDialog(item)
                }

                override fun onClickDelete(position: Int) {
                    val itemToDelete = shopItems[position]
                    shopViewModel.deleteShopItem(itemToDelete)

                    mainViewModel.removeFromCart(itemToDelete)
                }
            })
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = shopAdapter
        }
    }

    private fun showAddEditItemDialog(shopItem: ShopItem? = null) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.item_editor_dialog, null)
        val etItemName = dialogView.findViewById<EditText>(R.id.etItemName)
        val etItemDescription = dialogView.findViewById<EditText>(R.id.etItemDescription)
        val etItemPrice = dialogView.findViewById<EditText>(R.id.etItemPrice)
        val etItemCategory = dialogView.findViewById<EditText>(R.id.etItemCategory)
        val etItemQuantity = dialogView.findViewById<EditText>(R.id.etItemQuantity)

        shopItem?.let {
            etItemName.setText(it.name)
            etItemDescription.setText(it.description)
            etItemPrice.setText(it.price.toString())
            etItemCategory.setText(it.category)
            etItemQuantity.setText(it.quantity.toString())
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(if (shopItem == null) "Add New Item" else "Edit Item")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val name = etItemName.text.toString()
                val description = etItemDescription.text.toString()
                val price = etItemPrice.text.toString().toDoubleOrNull() ?: 0.0
                val category = etItemCategory.text.toString()
                val quantity = etItemQuantity.text.toString().toIntOrNull() ?: 1

                val newItem = ShopItem(
                    id = shopItem?.id ?: 0,
                    name = name,
                    description = description,
                    price = price,
                    category = category,
                    quantity = quantity
                )
                shopViewModel.addOrUpdateShopItem(newItem)
                mainViewModel.updateCartItem(newItem)

            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class ViewModelFactory(private val shopItemDao: ShopItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShopViewModel(shopItemDao) as T
        }
        throw IllegalArgumentException("Unexpected class $modelClass")
    }
}
