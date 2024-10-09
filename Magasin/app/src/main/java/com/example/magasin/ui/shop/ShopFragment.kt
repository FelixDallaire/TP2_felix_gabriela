package com.example.magasin.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.magasin.data.MagasinDatabase
import com.example.magasin.databinding.FragmentShopBinding
import com.example.magasin.data.ShopItemDao

class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    private lateinit var shopViewModel: ShopViewModel
    private lateinit var shopAdapter: ShopAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)

        val database = MagasinDatabase.getInstance(requireContext())
        val shopItemDao = database.shopItemDao()
        shopViewModel =
            ViewModelProvider(this, ViewModelFactory(shopItemDao)).get(ShopViewModel::class.java)

        setupRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shopViewModel.shopItems.observe(viewLifecycleOwner) { items ->
            shopAdapter.updateItems(items)
        }
    }

    private fun setupRecyclerView() {
        shopAdapter = ShopAdapter(mutableListOf()).apply {
            setOnItemClickListener(object : ShopAdapter.OnItemClickListenerInterface {
                override fun onItemClick(itemView: View?, position: Int) {
                    println("Item at position $position clicked")
                }

                override fun onClickEdit(itemView: View, position: Int) {
                    println("Edit item at position $position")
                }

                override fun onClickDelete(position: Int) {
                    println("Delete item at position $position")
                }
            })
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = shopAdapter
        }
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
