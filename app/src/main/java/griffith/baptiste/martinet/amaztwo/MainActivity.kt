package griffith.baptiste.martinet.amaztwo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.SearchView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
  private lateinit var db: DatabaseHelper
  private lateinit var menuCartItemsCount: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    db = DatabaseHelper.getInstance(this)

    val products = db.getProducts()
    val gridView = findViewById<GridView>(R.id.gridView)
    val adapter = GridAdapter(this, products)
    gridView.adapter = adapter

    menuCartItemsCount = findViewById(R.id.menuCartItemsCount)
    menuCartItemsCount.text = db.getNbProductsInCart().toString()

    val searchView: SearchView = findViewById(R.id.searchView)
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String): Boolean {
        gridView.apply {
          adapter.products = products.filter { product -> product.title.contains(query, true) }
          adapter.notifyDataSetChanged()
        }
        return false
      }
      override fun onQueryTextChange(newText: String): Boolean {
        gridView.apply {
          adapter.products = products.filter { product -> product.title.contains(newText, true) }
          adapter.notifyDataSetChanged()
        }
        return false
      }
    })
  }

  override fun onResume() {
    super.onResume()
    menuCartItemsCount.text = db.getNbProductsInCart().toString()
  }
}
