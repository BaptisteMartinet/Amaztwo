package griffith.baptiste.martinet.amaztwo

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ReviewActivity : AppCompatActivity() {
  private lateinit var db: DatabaseHelper
  private lateinit var menuCartItemsCount: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_review)

    menuCartItemsCount = findViewById(R.id.menuCartItemsCount)

    db = DatabaseHelper.getInstance(this)

    menuCartItemsCount.text = db.getNbProductsInCart().toString()

    val product: ProductValues = intent.extras?.get("product") as ProductValues

    val reviewRatingBar: RatingBar = findViewById(R.id.reviewRatingBar)
    val reviewEditText: EditText = findViewById(R.id.reviewEditText)
    val reviewSendBtn: Button = findViewById(R.id.reviewSendBtn)
    reviewSendBtn.setOnClickListener {
      val review = ReviewValues(
        getString(R.string.professor_name),
        reviewRatingBar.rating,
        reviewEditText.text.toString(),
        R.drawable.professor
      )
      db.insertReview(review, product.id.toLong(), db.writableDatabase)
      Toast.makeText(this, "Review successfully added", Toast.LENGTH_SHORT).show()
      finish()
    }
  }
}