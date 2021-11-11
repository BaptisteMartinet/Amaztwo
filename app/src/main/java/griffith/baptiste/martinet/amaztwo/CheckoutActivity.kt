package griffith.baptiste.martinet.amaztwo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class CheckoutActivity : AppCompatActivity() {
  private lateinit var menuCartItemsCount: TextView
  private lateinit var cartNbItems: TextView
  private lateinit var cartTotalCost: TextView
  private lateinit var adapter: RecyclerAdapter
  private lateinit var db: DatabaseHelper

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_checkout)

    db = DatabaseHelper.getInstance(this)
    val products = db.getProducts("WHERE nbInCart > 0")

    menuCartItemsCount = findViewById(R.id.menuCartItemsCount)
    cartNbItems = findViewById(R.id.cartNbItems)
    cartTotalCost = findViewById(R.id.cartTotalCost)
    adapter = RecyclerAdapter(products)

    val recyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
    recyclerView.adapter = adapter
    recyclerView.setRecyclerListener {
      updateCart()
    }

    updateCart()

    val cartCheckoutBtn = findViewById<Button>(R.id.cartCheckoutBtn)
    cartCheckoutBtn.setOnClickListener {
      if (adapter.dataSet.size <= 0) {
        Toast.makeText(this, "Nothing to checkout", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }
      if (Random.nextInt(0, 3) == 0)
        Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
      else {
        //update DB
        adapter.dataSet.forEach { db.updateProductStock(it.id, it.nbInCart) }
        //update frontend
        val dataSetSize = adapter.dataSet.size
        adapter.dataSet.clear()
        adapter.notifyItemRangeRemoved(0, dataSetSize)
        updateCart()
        Toast.makeText(this, "Success!", Toast.LENGTH_LONG).show()
      }
    }
  }

  private fun updateCart() {
    menuCartItemsCount.text = db.getNbProductsInCart().toString()
    val totalItems: Int = adapter.dataSet.sumOf { it.nbInCart }
    val totalCost: Double = adapter.dataSet.sumOf { it.price.toDouble() * it.nbInCart }
    cartNbItems.text = getString(R.string.cart_nb_items_placeholder).format(totalItems)
    cartTotalCost.text = getString(R.string.cart_amount).format(totalCost)
  }
}