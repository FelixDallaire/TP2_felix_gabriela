import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.magasin.model.ShopItem

class CartViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<List<ShopItem>>(emptyList())
    val cartItems: LiveData<List<ShopItem>> = _cartItems

    init {
//        addToCart(ShopItem(1, "Lamp", "A bright lamp", 20.00, "Home", 2, ""))
//        addToCart(ShopItem(2, "Chair", "Comfortable chair", 45.00, "Furniture", 1, ""))
    }

    fun addToCart(item: ShopItem) {
        val updatedItems = _cartItems.value.orEmpty() + item
        _cartItems.value = updatedItems
    }

    fun removeFromCart(item: ShopItem) {
        _cartItems.value = _cartItems.value.orEmpty().filter { it.id != item.id }
    }

    fun updateQuantity(item: ShopItem, quantity: Int) {
        _cartItems.value = _cartItems.value.orEmpty().map {
            if (it.id == item.id) it.copy(quantity = quantity) else it
        }
    }
}
