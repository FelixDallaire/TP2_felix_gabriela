package com.example.magasin.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.magasin.databinding.FragmentShopBinding

class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    private val shopViewModel: ShopViewModel by viewModels()

    private lateinit var shopItems: MutableList<ShopItem>
    private lateinit var shopAdapter: ShopAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shopItems = mutableListOf()

        shopAdapter = ShopAdapter(shopItems)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = shopAdapter
        }

        shopAdapter.setOnItemClickListener(object : ShopAdapter.OnItemClickListenerInterface {
            override fun onItemClick(itemView: View?, position: Int) {
                println("L'item a la position $position est cliqué")
            }

            override fun onClickEdit(itemView: View, position: Int) {
                println("L'item a la position $position est édite")
            }

            override fun onClickDelete(position: Int) {
                if (position >= 0 && position < shopItems.size) {
                    shopItems.removeAt(position)
                    shopAdapter.notifyItemRemoved(position)
                    shopAdapter.notifyItemRangeChanged(position, shopItems.size)
                }
            }
        })

        shopViewModel.shopItems.observe(viewLifecycleOwner, Observer { items ->
            shopItems.clear()
            shopItems.addAll(items)
            shopAdapter.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
