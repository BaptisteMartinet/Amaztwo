package griffith.baptiste.martinet.amaztwo

import java.io.Serializable

class ProductValues(
  var id: Int = -1,
  var title: String,
  var shortDesc: String,
  var longDesc: String,
  var price: Float,
  var pictureId: Int,
  var nbInStock: Int,
  var nbInCart: Int,
  var reviews: List<ReviewValues> = emptyList(),
  var reviewsRatingAverage: Float = 0f // Not stored in DB, computed when reviews are retrieved
) : Serializable

class ReviewValues(
  var username: String,
  var rating: Float,
  var text: String,
  var pictureId: Int = R.drawable.avatar_placeholder,
) : Serializable