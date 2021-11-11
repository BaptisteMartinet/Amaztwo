package griffith.baptiste.martinet.amaztwo

class DatabaseData {
  companion object {
    val data: List<ProductValues> = listOf(
      ProductValues(
        -1,
        "Wonderful tap",
        "Such an incredible tap.",
        "This tap support both hot and cold water. Like most tap you would say? Yep, you are completely right but this one is way better. Buy it please.",
        99.99f,
        R.drawable.tap,
        12,
        0,
        listOf(
          ReviewValues("Baptiste Martinet", 5f, "This tap made my life way easier. I can literally use it for anything water related."),
          ReviewValues("Alessandro Kurek", 0f, "So bad!!!!"),
          ReviewValues("Thomas Voster", 0f, "Do not dispense beer like I thought!")
        ),
      ),
      ProductValues(
        -1,
        "Modern Kitchen Sink Basin Mixer Tap",
        "Multiple chrome plated provided a lifetime mirror look on surface.",
        "Made of the finest copper materials, contains<0.25% total lead content by weighted average. Healthy and durable.",
        258.99f,
        R.drawable.modern_tap,
        13,
        0,
        listOf(
          ReviewValues("Baptiste Martinet", 3f, "There is no way this does not become the next big thing in 2022."),
        ),
      ),
      ProductValues(-1,
      "Kitchen Sink Mixer Taps Monobloc Swivel Spout Chrome",
      "Simplicity double handle, high Arc design, Suitable for majority sink.",
      "Ceramic disc cartridge for smooth and enduring operation. ad-free solid brass construction ensures a safer and healthier waterway.",
      19.99f,
      R.drawable.tap3,
      2,
      0,
        listOf(
          ReviewValues("Melina Parker", 0f, "My water tastes horrible through this tap as well. Its been a week and no better, did you ever get yours sorted?"),
          ReviewValues("TheHonestBloke", 5f, "This product rusted at the end after about 3 months of use...\n" +
              "Was really pleased originally, easy to install and felt sturdy and of good quality.\n" +
              "However it started to rust at the end on a seam near to where the water came out.\n" +
              "Got a refund and bought something else!")
        )
      ),
      ProductValues(-1,
        "Heable Kitchen Sink Mixer Tap with Pull Down Sprayer",
        "Modern commercial kitchen tap design make your kitchen room simple and fresh.",
        "Single handle design integrate to control water temperature and flow volume easily, high arc 360 degree swivel spout supply full range washing access, updated mechanical retraction system make pull down sprayer head always sit into original place after use.",
        49.39f,
        R.drawable.tap4,
        5,
        0,
        listOf(
          ReviewValues("EB", 5f, "So the moment we are living in very unusual times. (Covid19) Needed a new tap, and Buying from amazon was my only choice. Spent ages looking for a tap. It’s a bit hard to buy, unseen. Found this and immediately liked the look, and added features."),
          ReviewValues("Chris R", 0f, "I purchased this item as I liked the style and the price. Unfortunately it wasn't until it was fitted that we realised it was not suitable for gravity fed systems."),
          ReviewValues("VICARAGE3", 4f, "Over the years I have installed various taps in houses as we moved from one place to another and without doubt this has got to be the easiest ever.")
        )
      ),
      ProductValues(-1,
        "Funime Traditional Kitchen Sink Mixer Tap Elegant Ceramic Dual Lever",
        "First-tier brass & multilayer chrome finish, sturdy construction.",
        "Ceramic white dual lever and ceramic disc cartridge, nice appearance, no drips. Attached Gift: Free stainless steel hoses, installation kits and an instruction, easy installation.",
        59.99f,
        R.drawable.tap6,
        53,
        0,
        listOf(
          ReviewValues("Debbie", 5f, "Really good taps, look a lot more expensive than they are. Sleek and classy."),
          ReviewValues("Tilly", 1f, "Bought in November 2017 but tap not installed until one day ago with new kitchen sink. The tap handles are out of sync - they don't line up parallel to each other. The main problem is that the water trickles out and as a consequence there isn't enough pressure to get hot water - the pressure in the water system is not at fault as all other taps in the house work fine.")
        )
      ),
      ProductValues(-1,
        "Kitchen Sink Mixer Tap Single Lever with Long Swivel Spout",
        "EASY TO INSTALL.",
        "Swivel RANGE 360°: Swivel long spout - provide a practical and convenient solution to your kitchen needs.",
        59.99f,
        R.drawable.tap7,
        9,
        0,
        listOf(
          ReviewValues("ICEFE", 5f, "This replaced a similar tap we had purchased from B&Q 12 years ago."),
          ReviewValues("FraserD", 4f, "Fast delivery and all the parts were there when I opened to box it came in. Sadly, I didn’t inspect the tap closely enough or I would have sent it back for a replacement.")
        )
      ),
      ProductValues(-1,
        "Kitchen Mixer Tap with Pull-out Spout",
        "The classic chrome version with an extendable spray head.",
        "Generous action radius even outside the sink: Extendable spray head for easier filling of heavy pots and easy cleaning of large sinks.",
        225f,
        R.drawable.tap8,
        9,
        0,
        listOf(
          ReviewValues("GMoore", 4f, "Love this tap. Looks simply elegant. Really good, easy variation of pressure and temperature."),
          ReviewValues("Mary G", 5f, "Perfect!")
        )
      )//here
    )
  }
}