package com.example.magasin.ui.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.magasin.R
import com.example.magasin.databinding.ShopItemBinding
import com.example.magasin.model.ShopItem
import com.example.magasin.utils.CategoryImageUtils


/**
 * Adapter pour RecyclerView qui affiche des articles de magasin.
 * Gère également le mode administrateur pour modifier et supprimer des articles.
 * @property shopItems Liste mutable des articles de magasin.
 * @property isAdminMode Booléen pour déterminer si le mode administrateur est actif.
 */
class ShopAdapter(
    internal var shopItems: MutableList<ShopItem>,
    private var isAdminMode: Boolean = false
) :
    RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    /**
     * Met à jour le mode administrateur et notifie un changement de données.
     * @param isAdmin Nouvel état du mode administrateur.
     */
    fun updateAdminMode(isAdmin: Boolean) {
        isAdminMode = isAdmin
        notifyDataSetChanged()
    }

    /**
     * Interface pour la gestion des clics sur les articles.
     */
    interface OnItemClickListenerInterface {
        fun onItemClick(itemView: View?, position: Int)
        fun onClickEdit(itemView: View, position: Int)
        fun onClickDelete(position: Int)
    }

    lateinit var listener: OnItemClickListenerInterface

    /**
     * Définit l'écouteur pour les interactions avec les articles.
     * @param listener L'écouteur qui traite les clics sur les éléments.
     */
    fun setOnItemClickListener(listener: OnItemClickListenerInterface) {
        this.listener = listener
    }

    /**
     * Met à jour la liste des articles de magasin et notifie un changement de données.
     * @param newItems Nouvelle liste des articles à afficher.
     */
    fun updateItems(newItems: List<ShopItem>) {
        shopItems.clear()
        shopItems.addAll(newItems)
        notifyDataSetChanged()
    }

    /**
     * ViewHolder pour gérer l'affichage et l'interaction avec un article de magasin.
     */
    inner class ViewHolder(private val binding: ShopItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, adapterPosition)
                }
            }

            setupContextMenu()
        }

        /**
         * Configure le menu contextuel pour l'édition et la suppression d'articles.
         */
        private fun setupContextMenu() {
            binding.root.setOnCreateContextMenuListener { menu, v, _ ->
                if (isAdminMode) {
                    val position = adapterPosition
                    createMenuItem(menu, v.id, R.string.action_edit) {
                        listener.onClickEdit(itemView, position)
                    }
                    createMenuItem(menu, v.id, R.string.action_delete) {
                        listener.onClickDelete(position)
                    }
                }
            }
        }

        /**
         * Crée un élément de menu contextuel.
         * @param menu Menu contextuel.
         * @param groupId Identifiant du groupe pour l'élément de menu.
         * @param titleResId Identifiant de la ressource de la chaîne pour le titre de l'élément de menu.
         * @param action Action à exécuter lorsque l'élément de menu est sélectionné.
         */
        private fun createMenuItem(
            menu: android.view.ContextMenu,
            groupId: Int,
            titleResId: Int,
            action: () -> Unit
        ) {
            val menuItem = menu.add(0, groupId, 0, titleResId)
            menuItem.setOnMenuItemClickListener {
                action()
                false
            }
        }


        /**
         * Associe un article de magasin avec ce ViewHolder.
         * @param shopItem L'article à afficher et gérer.
         */
        fun bind(shopItem: ShopItem) {
            binding.tvProductName.text = shopItem.name
            binding.tvProductDescription.text = shopItem.description
            binding.tvProductPrice.text = String.format("%.2f", shopItem.price)

            val imageResId = CategoryImageUtils.getImageResIdForCategory(shopItem.category)
            binding.ivProductImage.setImageResource(imageResId)
        }
    }

    /**
     * Crée et retourne un ViewHolder pour l'article de magasin.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ShopItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    /**
     * Associe les données de l'article à un ViewHolder spécifique.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shopItem = shopItems[position]
        holder.bind(shopItem)
    }

    /**
     * Retourne le nombre total d'articles dans l'adaptateur.
     */
    override fun getItemCount(): Int = shopItems.size
}
