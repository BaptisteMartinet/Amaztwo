package griffith.baptiste.martinet.amaztwo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.io.Serializable
import java.lang.Exception

class ProductActivity : AppCompatActivity() {
  private lateinit var stockStatusTextView: TextView
  private lateinit var menuCartItemsCount: TextView
  private lateinit var db: DatabaseHelper
  private lateinit var adapter: CommentAdapter
  private var productId: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_product)

    /* setting up global variables */

    stockStatusTextView = findViewById(R.id.stockStatus)
    menuCartItemsCount = findViewById(R.id.menuCartItemsCount)

    db = DatabaseHelper.getInstance(this)

    val product: ProductValues = intent.extras?.get("product") as ProductValues
    productId = product.id
    /* updating view */

    val img = findViewById<ImageView>(R.id.picture)
    val title = findViewById<TextView>(R.id.title)
    val productId = findViewById<TextView>(R.id.productId)
    val shortDesc = findViewById<TextView>(R.id.shortDesc)
    val longDesc = findViewById<TextView>(R.id.longDesc)
    val price = findViewById<TextView>(R.id.price)
    val rating = findViewById<RatingBar>(R.id.rating)
    val commentsListView = findViewById<ListView>(R.id.commentsListView)
    commentsListView.isFocusable = false //Used to prevent the scrollview to start at the bottom

    img.setImageResource(product.pictureId)
    title.text = product.title
    productId.text = getString(R.string.product_id_placeholder).format(product.id)
    shortDesc.text = product.shortDesc
    longDesc.text = product.longDesc
    price.text =
      getString(R.string.product_price_placeholder).format(product.price)
    rating.rating = product.reviewsRatingAverage
    adapter = CommentAdapter(product.reviews)
    commentsListView.adapter = adapter
    commentsListView.setOnItemClickListener { _, _, _, _ ->
      val intent = Intent(this, ReviewActivity::class.java)
      intent.putExtra("product", product as Serializable)
      startActivity(intent)
    }

    /* others */

    menuCartItemsCount.text = db.getNbProductsInCart().toString()
    updateStockStatus()

    val addToCartBtn = findViewById<Button>(R.id.addToCartBtn)
    addToCartBtn.setOnClickListener {
      if (db.addProductToCart(product.id)) {
        menuCartItemsCount.text = db.getNbProductsInCart().toString()
        Toast.makeText(this, "Product added to cart", Toast.LENGTH_SHORT).show()
      } else {
        Toast.makeText(this, "You've reach the stock limit", Toast.LENGTH_LONG).show()
      }
    }
  }

  private fun updateStockStatus() {
    try {
      val (nbInStock, _) = db.getProductStockInfo(productId)
      if (nbInStock <= 0) {
        stockStatusTextView.text = getString(R.string.product_stock_status_unavailable_placeholder)
        stockStatusTextView.setTextColor(Color.parseColor("#aa5d55"))
      } else {
        stockStatusTextView.text = getString(R.string.product_stock_status_placeholder).format(nbInStock)
      }
    } catch (e: Exception) {
      println(e.message)
    }
  }

  override fun onResume() {
    super.onResume()
    menuCartItemsCount.text = db.getNbProductsInCart().toString()
    adapter.dataSource = db.getProductReviews(productId)
    adapter.notifyDataSetChanged()
    updateStockStatus()
  }
}
