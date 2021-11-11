package griffith.baptiste.martinet.amaztwo

import android.content.Context
import android.view.LayoutInflater
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import java.io.Serializable

class GridAdapter(private val context: Context, var products: List<ProductValues>) : BaseAdapter() {
  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    val view: View = convertView ?: LayoutInflater.from(parent?.context)
      .inflate(R.layout.grid_product, parent, false)

    val cardImage = view.findViewById<ImageView>(R.id.cardImage)
    val cardTitle = view.findViewById<TextView>(R.id.cardTitle)
    val cardRating = view.findViewById<RatingBar>(R.id.cardRating)
    val cardPrice = view.findViewById<TextView>(R.id.cardPrice)

    val product: ProductValues = getItem(position)

    cardImage.setImageResource(product.pictureId)
    cardTitle.text = product.title
    cardRating.rating = product.reviewsRatingAverage
    cardPrice.text =
      parent!!.context.getString(R.string.product_price_placeholder).format(product.price)

    view.setOnClickListener {
      val intent = Intent(context, ProductActivity::class.java)
      intent.putExtra("product", product as Serializable)
      context.startActivity(intent)
    }
    return view
  }

  override fun getItem(position: Int): ProductValues = products[position]

  override fun getItemId(position: Int): Long = 0

  override fun getCount(): Int = products.size
}
