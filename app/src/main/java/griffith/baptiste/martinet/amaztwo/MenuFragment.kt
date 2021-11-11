package griffith.baptiste.martinet.amaztwo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

class MenuFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_menu, container, false)

    val menuMainBtn = view.findViewById<ImageButton>(R.id.menuMainBtn)
    menuMainBtn?.setOnClickListener {
      val intent = Intent(activity, MainActivity::class.java)
      intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
      startActivity(intent)
    }

    val cartBtn = view.findViewById<ImageButton>(R.id.cartBtn)
    cartBtn.setOnClickListener {
      val intent = Intent(activity, CheckoutActivity::class.java)
      intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
      startActivity(intent)
    }
    return view
  }
}
