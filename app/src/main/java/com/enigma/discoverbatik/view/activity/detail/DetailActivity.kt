package com.enigma.discoverbatik.view.activity.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.enigma.discoverbatik.data.remote.response.BatikItem
import com.enigma.discoverbatik.data.remote.response.DetailResponse
import com.enigma.discoverbatik.data.remote.service.ApiService
import com.enigma.discoverbatik.databinding.ActivityDetailBinding
import com.enigma.discoverbatik.di.Injection
import com.enigma.discoverbatik.utils.CommonUtils
import com.enigma.discoverbatik.view.activity.cart.CartManager
import com.enigma.discoverbatik.view.fragment.favorite.FavoriteManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var cartManager: CartManager
    private lateinit var apiService: ApiService
    private lateinit var favoriteManager: FavoriteManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        cartManager = CartManager.getInstance()
        apiService = Injection.proviceApiService()
        favoriteManager = FavoriteManager.getInstance()

        detailBinding.apply {
            btnBugReport.setOnClickListener {
                CommonUtils.alertThanks(this@DetailActivity, "Thanks for reporting annoying bug🫵😭")
            }
            btnBack.setOnClickListener {
                finish()
            }
            btnBuy.setOnClickListener {
                onButButtonClicked()
            }
        }

        val repository = Injection.provideRepository(this@DetailActivity)
        val factory = ViewModelFactory(repository)
        detailViewModel =
            ViewModelProvider(this@DetailActivity, factory)[DetailViewModel::class.java]

        getDetailId()
        getDetail()
        generateRandomRatingNumber()

    }


    private fun getDetail() {
        detailViewModel.batikDetail.observe(this@DetailActivity) { content ->
            if (content != null) {
                detailBinding.apply {
                    Glide.with(this@DetailActivity)
                        .load(content.photoUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivContentImage)
                }
                detailBinding.tvContentTitle.text = content.batikName
                detailBinding.tvContentLocation.text = content.asalDaerah
                detailBinding.tvContentPrice.text = CommonUtils.formatRupiah(content.price!!)
                detailBinding.tvContentTechnique.text = content.technique
                detailBinding.tvContentDescription.text = content.content

                detailBinding.apply {
                    btnFavorite.setOnClickListener {
                        val itemId = content.id.toString()
                        val itemName = content.batikName
                        val itemLocation = content.asalDaerah
                        val itemDescription = content.content
                        favoriteManager.addToFavorites(itemId,
                            itemName!!, itemLocation!!, itemDescription!!)

                        CommonUtils.alertThanks(this@DetailActivity, "Item has been added to Favorite Activity, thanks 😭")
                    }
                }

            }
        }
    }

    private fun onButButtonClicked() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                //  Mendapatkan detail item batik berdasarkan ID
                val detailResponse = apiService.getDetailById(getDetailIdForCarManager())

                withContext(Dispatchers.Main) {
                    addToCart(detailResponse)
                    CommonUtils.alertThanks(this@DetailActivity, "Item Added to Cart")
                }
            } catch (e: Exception){
                withContext(Dispatchers.Main) {
                    CommonUtils.alertError(this@DetailActivity, "Failed to fetch item details")
                }
            }
        }
    }

    private fun addToCart(item: DetailResponse) {
        val itemId = item.id ?: 0
        val itemName = item.batikName ?: ""
        val itemPrice = item.price ?: 0

        val newItem = BatikItem(
            id = itemId.toString(),
            name = itemName,
            price = itemPrice,
            quantity = 1
        )

        cartManager.addItem(newItem)
    }

    private fun generateRandomRatingNumber() {
        val random = (1.1 + Math.random() * (5.0 - 1.1)).toFloat()
        val formattedNumber = String.format("%.2f", random)

        detailBinding.tvRating.text = formattedNumber
    }

    private fun getDetailIdForCarManager(): Int {
        return intent.getIntExtra("extra_id", - 1)
    }


    private fun getDetailId() {
        val id = intent.getIntExtra("extra_id", -1)
        Log.d("Waduh id", "Id Item : $id")
        detailViewModel.getDetailById(id)
    }
}