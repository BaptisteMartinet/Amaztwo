package griffith.baptiste.martinet.amaztwo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(val dataSet: MutableList<ProductValues>) :
  RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
  private lateinit var db: DatabaseHelper

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val itemImage: ImageView = view.findViewById(R.id.itemImage)
    val itemTitle: TextView = view.findViewById(R.id.itemTitle)
    val itemPrice: TextView = view.findViewById(R.id.itemPrice)
    val itemNbItems: TextView = view.findViewById(R.id.itemNbItems)
    val itemTrash: ImageButton = view.findViewById(R.id.itemTrash)
  }

  override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
    val view =
      LayoutInflater.from(viewGroup.context).inflate(R.layout.recycler_item, viewGroup, false)
    db = DatabaseHelper.getInstance(viewGroup.context)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
    val product = dataSet[position]
    viewHolder.itemImage.setImageResource(product.pictureId)
    viewHolder.itemTitle.text = product.title
    viewHolder.itemPrice.text =
      viewHolder.itemView.context.getString(R.string.product_price_placeholder)
        .format(product.price)
    viewHolder.itemNbItems.text =
      viewHolder.itemView.context.getString(R.string.product_quantity_placeholder)
        .format(product.nbInCart)
    viewHolder.itemTrash.setOnClickListener {
      this.dataSet[position].nbInCart -= 1
      db.updateProductNbInCart(this.dataSet[position].id, this.dataSet[position].nbInCart)
      if (this.dataSet[position].nbInCart <= 0) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
      } else {
        notifyItemChanged(position)
      }
    }
  }

  override fun getItemCount() = dataSet.size
}
