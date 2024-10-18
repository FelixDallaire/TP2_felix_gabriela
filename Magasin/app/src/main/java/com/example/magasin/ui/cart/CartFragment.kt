package com.example.magasin.ui.cart

import CartViewModel
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

    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        // Initialize the RecyclerView
        binding.rvCartItems.layoutManager = LinearLayoutManager(context)
        binding.rvCartItems.setHasFixedSize(true)

        // Observe the LiveData from ViewModel
        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter = CartAdapter(items)
            binding.rvCartItems.adapter = cartAdapter
            updateTotalPrice(items)
        }

        return binding.root
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
