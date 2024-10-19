package com.example.magasin.ui.shop

import MainViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            ViewModelProvider(this, ViewModelFactory(shopItemDao)).get(ShopViewModel::class.java)

        setupRecyclerView()



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the MainViewModel
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        Log.d("isAdmin",mainViewModel.toString())

        shopViewModel.shopItems.observe(viewLifecycleOwner) { items ->
            shopAdapter.updateItems(items)
        }

        mainViewModel.isAdmin.observe(viewLifecycleOwner) { isAdmin ->
            binding.floatingActionButton.visibility = if (isAdmin) View.VISIBLE else View.GONE
        }


    }

    private fun setupRecyclerView() {
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)


        mainViewModel.isAdmin.observe(viewLifecycleOwner) { isAdmin ->

        Log.d("isAdmin", "setup isAdmin: $isAdmin ")
        }

        shopAdapter = ShopAdapter(mutableListOf(),this,mainViewModel).apply {
            setOnItemClickListener(object : ShopAdapter.OnItemClickListenerInterface {
                override fun onItemClick(itemView: View?, position: Int) {
                    val item = shopAdapter.shopItems[position]
                    mainViewModel.addToCart(item)

                    Toast.makeText(context, "Item ajouté au panier", Toast.LENGTH_SHORT).show()
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
