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

/**
 * Un Fragment pour afficher et gérer le panier d'achats de l'utilisateur.
 */
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var cartAdapter: CartAdapter

    /**
     * Crée et retourne la vue de ce fragment, initialisant le binding pour accéder aux composants de l'interface utilisateur.
     *
     * @param inflater L'inflater utilisé pour gonfler les layouts du fragment.
     * @param container Le conteneur parent dans lequel le fragment est inséré.
     * @param savedInstanceState Un Bundle contenant des données précédemment enregistrées par le fragment.
     * @return La vue racine du fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Appelé immédiatement après onCreateView. Initialise le ViewModel et configure le RecyclerView.
     *
     * @param view La vue racine du fragment.
     * @param savedInstanceState Un Bundle qui contient l'état précédemment enregistré du fragment.
     */
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

    /**
     * Configure le RecyclerView utilisé pour afficher les articles du panier.
     */
    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(mutableListOf())
        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }
    }

    /**
     * Calcule et affiche le prix total des articles dans le panier.
     * @param items La liste des articles dans le panier.
     */
    private fun updateTotalPrice(items: List<ShopItem>) {
        val total = items.sumOf { it.price * it.quantity }
        binding.tvTotalPrice.text = getString(R.string.total_price, total)
    }

    /**
     * Nettoie les ressources lors de la destruction de la vue du fragment.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
