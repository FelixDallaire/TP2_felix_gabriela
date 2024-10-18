import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.magasin.model.ShopItem

class MainViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<List<ShopItem>>(emptyList())
    val cartItems: LiveData<List<ShopItem>> get() = _cartItems

    fun addToCart(item: ShopItem) {
        val currentItems = _cartItems.value ?: emptyList()
        _cartItems.value = currentItems + item
    }
}
