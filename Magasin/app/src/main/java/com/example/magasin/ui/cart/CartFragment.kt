package com.example.magasin.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.magasin.databinding.FragmentCartBinding
import com.example.magasin.model.ShopItem
import com.example.magasin.ui.shop.ShopAdapter
import java.util.ArrayList

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var mItemList: MutableList<ShopItem>
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cartViewModel =
            ViewModelProvider(this).get(CartViewModel::class.java)

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvItems.setHasFixedSize(true)

        val Items = ShopItem(1,"item", "",10.0,"",1, "")
        val Iteme = ShopItem(2,"chevalamp", "",10.0,"",100, "")

        mItemList = ArrayList<ShopItem>()

        mItemList.add(Items)
        mItemList.add(Iteme)

        Log.d("CartFragment",mItemList.toString())



        cartAdapter = CartAdapter(mItemList)

        var coutTotal : Double = 0.0

        mItemList.forEach { item-> coutTotal += item.price * item.quantity }

        binding.tvTotal.text =(coutTotal).toString()

        binding.rvItems.adapter = cartAdapter
        Log.d("CartFragment", "LinearLayoutManager has been set")

        binding.rvItems.layoutManager = LinearLayoutManager(context)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}