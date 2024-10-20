package com.example.magasin.ui.cart

import MainViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.magasin.R
import com.example.magasin.databinding.FragmentCartBinding
import com.example.magasin.model.ShopItem

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        setupRecyclerView()

        mainViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.cartItems = items
            cartAdapter.notifyDataSetChanged()
            updateTotalPrice(items)
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(mutableListOf())
        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }
    }

    private fun updateTotalPrice(items: List<ShopItem>) {
        val total = items.sumOf { it.price * it.quantity }
        binding.tvTotalPrice.text = getString(R.string.total_price, total)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
