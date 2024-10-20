import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.magasin.model.ShopItem

class MainViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<MutableList<ShopItem>>(mutableListOf())
    val cartItems: LiveData<MutableList<ShopItem>> = _cartItems

    private val _isAdminMode = MutableLiveData<Boolean>(false)
    val isAdminMode: LiveData<Boolean> = _isAdminMode

    fun toggleAdminMode() {
        _isAdminMode.value = !(_isAdminMode.value ?: false)
    }

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

    fun removeFromCart(item: ShopItem) {
        _cartItems.value?.let { cart ->
            cart.removeAll { it.id == item.id }
            _cartItems.value = cart
        }
    }
}
