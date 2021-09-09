package com.example.memebot


import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
     var memeUrl: String? = null
    val imageView: ImageView by lazy { findViewById<ImageView>(R.id.memeView) }
    val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.progressBar) }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()

    }
    private fun loadMeme(){

// ...


        progressBar.visibility = View.VISIBLE
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                memeUrl = response.getString("url")

                Glide.with(this).load(memeUrl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }
                }).into(imageView)
            },
            Response.ErrorListener {
                Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show()
            }
        )

         MySingleton.MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
// Add the request to the RequestQueue.

    }
    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type ="text/plane"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey,Check out this cool meme I saw in Reddit $memeUrl")
        val chooser= Intent.createChooser(intent,"Share this meme with...")
        startActivity(chooser)

    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}