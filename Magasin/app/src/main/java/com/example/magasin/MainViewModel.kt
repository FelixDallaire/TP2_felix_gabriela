import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.magasin.model.ShopItem

class MainViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<MutableList<ShopItem>>(mutableListOf())
    val cartItems: MutableLiveData<MutableList<ShopItem>> = _cartItems

    fun addToCart(item: ShopItem, quantity: Int = 1) {
        val existingItem = _cartItems.value?.find { it.id == item.id }
        if (existingItem != null) {
            existingItem.quantity += quantity
            _cartItems.value = _cartItems.value // Trigger LiveData update
        } else {
            val newItem = item.copy(quantity = quantity)
            _cartItems.value = _cartItems.value?.apply { add(newItem) }
        }
    }
}
