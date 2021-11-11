package griffith.baptiste.martinet.amaztwo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView

class CommentAdapter(var dataSource: List<ReviewValues>) :
  BaseAdapter() {

  override fun getCount(): Int = dataSource.size

  override fun getItem(pos: Int): ReviewValues = dataSource[pos]

  override fun getItemId(pos: Int): Long = pos.toLong()

  override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {
    val view: View = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.list_comment, parent, false)

    val commentUsername = view.findViewById<TextView>(R.id.commentUsername)
    val commentRatingBar = view.findViewById<RatingBar>(R.id.commentRating)
    val commentText = view.findViewById<TextView>(R.id.commentText)
    val commentImage = view.findViewById<ImageView>(R.id.commentImage)

    val comment = getItem(pos)
    commentUsername.text = comment.username
    commentRatingBar.rating = comment.rating
    commentText.text = comment.text
    commentImage.setImageResource(comment.pictureId)
    return view
  }
}