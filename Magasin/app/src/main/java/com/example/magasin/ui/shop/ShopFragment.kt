package com.example.magasin.ui.shop

import MainViewModel
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
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
    }

    private fun setupRecyclerView() {
        shopAdapter = ShopAdapter(mutableListOf(), false).apply {
            setOnItemClickListener(object : ShopAdapter.OnItemClickListenerInterface {
                override fun onItemClick(itemView: View?, position: Int) {
                    val item = shopItems[position]
                    mainViewModel.addToCart(item)
                }

                override fun onClickEdit(itemView: View, position: Int) {
                    // Edit logic
                }

                override fun onClickDelete(position: Int) {
                    // Delete logic
                }
            })
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = shopAdapter
        }
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
