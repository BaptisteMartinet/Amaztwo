package griffith.baptiste.martinet.amaztwo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper
private constructor(context: Context) :
  SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
  companion object {
    private var sqlInstance : DatabaseHelper? = null
    private const val DATABASE_NAME = "amaztwo"
    private const val DATABASE_VERSION = 1

    @Synchronized
    fun getInstance(context: Context): DatabaseHelper {
      if (sqlInstance == null) {
        sqlInstance = DatabaseHelper(context.applicationContext)
      }
      return sqlInstance!!
    }
  }

  private val productsTableName: String = "products"
  private val reviewsTableName: String = "reviews"

  private val createProductTableQuery =
    "CREATE TABLE IF NOT EXISTS $productsTableName (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "title VARCHAR(256)," +
        "shortDesc VARCHAR(256)," +
        "longDesc VARCHAR(2048)," +
        "price FLOAT," +
        "pictureId INTEGER," +
        "nbInStock INTEGER," +
        "nbInCart INTEGER" +
        ")"

  private val createReviewTableQuery =
    "CREATE TABLE IF NOT EXISTS $reviewsTableName (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "productId INTEGER," +
        "username VARCHAR(256)," +
        "rating FLOAT," +
        "text VARCHAR(2048)," +
        "pictureId INTEGER" +
        ")"

  private val deleteProductTableQuery = "DROP TABLE $productsTableName"
  private val deleteReviewTableQuery = "DROP TABLE $reviewsTableName"

  private fun createDB(db: SQLiteDatabase?) {
    Log.i("DATABASE", "Creating database...")
    db?.execSQL(createProductTableQuery)
    db?.execSQL(createReviewTableQuery)
    insertProducts(DatabaseData.data, db!!)
  }

  private fun upgradeDB(db: SQLiteDatabase?) {
    Log.i("DATABASE", "Upgrading database...")
    db?.execSQL(deleteReviewTableQuery)
    db?.execSQL(deleteProductTableQuery)
    createDB(db)
  }

  override fun onCreate(db: SQLiteDatabase?) {
    createDB(db)
  }

  override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    upgradeDB(db)
  }

  override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    upgradeDB(db)
  }

  fun insertReview(review: ReviewValues, productId: Long, db: SQLiteDatabase): Boolean {
    val contentValues = ContentValues()
    contentValues.put("productId", productId)
    contentValues.put("username", review.username)
    contentValues.put("rating", review.rating)
    contentValues.put("text", review.text)
    contentValues.put("pictureId", review.pictureId)
    val result = db.insert(this.reviewsTableName, null, contentValues)
    return (result > 0)
  }

  private fun insertReviews(
    reviews: List<ReviewValues>,
    productId: Long,
    db: SQLiteDatabase
  ): Boolean {
    reviews.forEach { review ->
      if (!insertReview(review, productId, db))
        return false
    }
    return true
  }

  private fun insertProduct(product: ProductValues, db: SQLiteDatabase): Boolean {
    val contentValues = ContentValues()
    contentValues.put("title", product.title)
    contentValues.put("shortDesc", product.shortDesc)
    contentValues.put("longDesc", product.longDesc)
    contentValues.put("price", product.price)
    contentValues.put("pictureId", product.pictureId)
    contentValues.put("nbInStock", product.nbInStock)
    contentValues.put("nbInCart", product.nbInCart)
    val productId = db.insert(this.productsTableName, null, contentValues)
    Log.i("DATABASE", "Inserting new product with id: $productId.")
    if (productId <= 0)
      return false
    return insertReviews(product.reviews, productId, db)
  }

  private fun insertProducts(products: List<ProductValues>, db: SQLiteDatabase): Boolean {
    for (product in products) {
      if (!insertProduct(product, db))
        return false
    }
    return true
  }

  fun getProductReviews(productId: Int): List<ReviewValues> {
    val reviews: MutableList<ReviewValues> = mutableListOf()
    val db = sqlInstance!!.readableDatabase
    val cursor: Cursor = db.rawQuery("SELECT * FROM $reviewsTableName WHERE productId = $productId", null)

    if (!cursor.moveToFirst())
      return reviews
    do {
      val review = ReviewValues(
        cursor.getString(cursor.getColumnIndexOrThrow("username")),
        cursor.getFloat(cursor.getColumnIndexOrThrow("rating")),
        cursor.getString(cursor.getColumnIndexOrThrow("text")),
        cursor.getInt(cursor.getColumnIndexOrThrow("pictureId"))
      )
      reviews.add(review)
    } while (cursor.moveToNext())
    cursor.close()
    return reviews
  }

  fun getProducts(where: String = ""): MutableList<ProductValues> {
    val products: MutableList<ProductValues> = mutableListOf()
    val db = sqlInstance!!.readableDatabase
    val cursor: Cursor = db.rawQuery("SELECT * FROM $productsTableName $where", null)

    if (!cursor.moveToFirst())
      return products
    do {
      val productId = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
      val reviews: List<ReviewValues> = getProductReviews(productId)
      val reviewsRatingAverage: Float = reviews.map { review -> review.rating }.average().toFloat()
      val product = ProductValues(
        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
        cursor.getString(cursor.getColumnIndexOrThrow("title")),
        cursor.getString(cursor.getColumnIndexOrThrow("shortDesc")),
        cursor.getString(cursor.getColumnIndexOrThrow("longDesc")),
        cursor.getFloat(cursor.getColumnIndexOrThrow("price")),
        cursor.getInt(cursor.getColumnIndexOrThrow("pictureId")),
        cursor.getInt(cursor.getColumnIndexOrThrow("nbInStock")),
        cursor.getInt(cursor.getColumnIndexOrThrow("nbInCart")),
        reviews,
        reviewsRatingAverage
      )
      products.add(product)
    } while (cursor.moveToNext())
    cursor.close()
    return products
  }

  fun getProductStockInfo(productId: Int): Pair<Int, Int> {
    val db = sqlInstance!!.readableDatabase
    val cursor: Cursor = db.rawQuery("SELECT nbInStock, nbInCart FROM $productsTableName WHERE id = $productId LIMIT 1", null)
    if (!cursor.moveToFirst())
      throw Exception("Product not found")
    val nbInStock: Int = cursor.getInt(cursor.getColumnIndexOrThrow("nbInStock"))
    val nbInCart: Int = cursor.getInt(cursor.getColumnIndexOrThrow("nbInCart"))
    cursor.close()
    return Pair(nbInStock, nbInCart)
  }

  fun getNbProductsInCart(): Int {
    val db = sqlInstance!!.readableDatabase
    val cursor: Cursor = db.rawQuery("SELECT SUM(nbInCart) FROM $productsTableName", null)
    try {
      if (cursor.moveToFirst())
        return cursor.getInt(0)
    } catch (e: Exception) {
      println(e.message)
    } finally {
      cursor.close()
    }
    return 0
  }

  fun addProductToCart(productId: Int) : Boolean {
    try {
      val (nbInStock, nbInCart) = this.getProductStockInfo(productId)
      if (nbInCart >= nbInStock)
        return false
      val db = sqlInstance!!.writableDatabase
      val cursor: Cursor = db.rawQuery("UPDATE $productsTableName SET nbInCart = nbInCart + 1 WHERE id = $productId", null)
      cursor.moveToFirst()
      cursor.close()
      return true
    } catch (e: Exception) {
      println(e.message)
    }
    return false
  }

  fun updateProductStock(productId: Int, nbItems: Int) : Boolean {
    try {
      val (nbInStock, _) = this.getProductStockInfo(productId)
      if (nbInStock - nbItems < 0)
        return false
      val db = sqlInstance!!.writableDatabase
      val cursor: Cursor = db.rawQuery("UPDATE $productsTableName SET nbInStock = nbInStock - $nbItems, nbInCart = 0 WHERE id = $productId", null)
      cursor.moveToFirst()
      cursor.close()
      return true
    } catch (e: Exception) {
      println(e.message)
    }
    return false
  }

  fun updateProductNbInCart(productId: Int, nbInCart: Int): Boolean {
    try {
      val db = sqlInstance!!.writableDatabase
      val cursor: Cursor = db.rawQuery("UPDATE $productsTableName SET nbInCart = $nbInCart WHERE id = $productId", null)
      cursor.moveToFirst()
      cursor.close()
      return true
    } catch (e: Exception) {
      println(e.message)
    }
    return false
  }
}
