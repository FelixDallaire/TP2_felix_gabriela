import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.magasin.model.ShopItem

/**
 * ViewModel principal pour gérer les données de l'application, notamment les articles du panier et le mode administrateur.
 */
class MainViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<MutableList<ShopItem>>(mutableListOf())
    val cartItems: LiveData<MutableList<ShopItem>> = _cartItems

    private val _isAdminMode = MutableLiveData<Boolean>(false)
    val isAdminMode: LiveData<Boolean> = _isAdminMode

    /**
     * Bascule le mode administrateur entre activé et désactivé.
     */
    fun toggleAdminMode() {
        _isAdminMode.value = !(_isAdminMode.value ?: false)
    }

    /**
     * Ajoute un article au panier ou augmente sa quantité si déjà présent.
     * @param item L'article à ajouter au panier.
     * @param quantity La quantité à ajouter.
     */
    fun addToCart(item: ShopItem, quantity: Int = 1) {
        val existingItem = _cartItems.value?.find { it.id == item.id }
        if (existingItem != null) {
            existingItem.quantity += quantity
            _cartItems.value = _cartItems.value
        } else {
            val newItem = item.copy(quantity = quantity)
            _cartItems.value = _cartItems.value?.apply { add(newItem) }
        }
    }

    /**
     * Met à jour un article dans le panier.
     * @param updatedItem L'article mis à jour.
     */
    fun updateCartItem(updatedItem: ShopItem) {
        _cartItems.value?.let { cart ->
            val cartItem = cart.find { it.id == updatedItem.id }
            if (cartItem != null) {
                cartItem.name = updatedItem.name
                cartItem.description = updatedItem.description
                cartItem.price = updatedItem.price
                cartItem.category = updatedItem.category
                _cartItems.value = cart
            }
        }
    }

    /**
     * Retire un article du panier.
     * @param item L'article à retirer.
     */
    fun removeFromCart(item: ShopItem) {
        _cartItems.value?.let { cart ->
            cart.removeAll { it.id == item.id }
            _cartItems.value = cart
        }
    }
}
