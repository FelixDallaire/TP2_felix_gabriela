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

        setupViewModels()
        setupRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeAdminMode()
        observeShopItems()
        setupFabListener()
    }

    private fun setupViewModels() {
        val database = MagasinDatabase.getInstance(requireContext())
        val shopItemDao = database.shopItemDao()

        shopViewModel = ViewModelProvider(this, ViewModelFactory(shopItemDao)).get(ShopViewModel::class.java)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    private fun setupRecyclerView() {
        shopAdapter = ShopAdapter(mutableListOf(), isAdminMode = false).apply {
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

        binding.rvShopProductList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = shopAdapter
        }
    }

    private fun observeAdminMode() {
        mainViewModel.isAdminMode.observe(viewLifecycleOwner) { isAdmin ->
            binding.fabAddProduct.visibility = if (isAdmin) View.VISIBLE else View.GONE
            shopAdapter.updateAdminMode(isAdmin)
        }
    }

    private fun observeShopItems() {
        shopViewModel.shopItems.observe(viewLifecycleOwner) { items ->
            shopAdapter.updateItems(items)
        }
    }

    private fun setupFabListener() {
        binding.fabAddProduct.setOnClickListener {
            showAddEditItemDialog()
        }
    }

    private fun showAddEditItemDialog(shopItem: ShopItem? = null) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.item_editor_dialog, null)
        val etItemName = dialogView.findViewById<EditText>(R.id.et_product_name)
        val etItemDescription = dialogView.findViewById<EditText>(R.id.et_product_description)
        val etItemPrice = dialogView.findViewById<EditText>(R.id.et_product_price)
        val etItemCategory = dialogView.findViewById<EditText>(R.id.et_product_category)
        val etItemQuantity = dialogView.findViewById<EditText>(R.id.et_product_quantity)

        fillDialogFields(shopItem, etItemName, etItemDescription, etItemPrice, etItemCategory, etItemQuantity)

        AlertDialog.Builder(requireContext())
            .setTitle(if (shopItem == null) R.string.dialog_new_item else R.string.dialog_edit_item)
            .setView(dialogView)
            .setPositiveButton(R.string.dialog_save) { _, _ ->
                saveItemFromDialog(shopItem, etItemName, etItemDescription, etItemPrice, etItemCategory, etItemQuantity)
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .create()
            .show()
    }

    private fun fillDialogFields(
        shopItem: ShopItem?,
        etItemName: EditText,
        etItemDescription: EditText,
        etItemPrice: EditText,
        etItemCategory: EditText,
        etItemQuantity: EditText
    ) {
        shopItem?.let {
            etItemName.setText(it.name)
            etItemDescription.setText(it.description)
            etItemPrice.setText(it.price.toString())
            etItemCategory.setText(it.category)
            etItemQuantity.setText(it.quantity.toString())
        }
    }

    private fun saveItemFromDialog(
        shopItem: ShopItem?,
        etItemName: EditText,
        etItemDescription: EditText,
        etItemPrice: EditText,
        etItemCategory: EditText,
        etItemQuantity: EditText
    ) {
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
