package com.example.memeshare

import android.app.Notification
import android.content.Intent
import android.drm.DrmStore
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var imageurl : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadmeme()
    }

    private fun loadmeme() {

        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme/wholesomememes"
        val circle = findViewById<ProgressBar>(R.id.progressBar)
        circle.visibility = View.VISIBLE

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(


            Request.Method.GET, url, null,
            Response.Listener { response ->
                imageurl = response.getString("url")

                val memeImageView = findViewById<ImageView>(R.id.imageView)
                Glide.with(this ).load(imageurl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        val circle = findViewById<ProgressBar>(R.id.progressBar)
                        circle.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        val circle = findViewById<ProgressBar>(R.id.progressBar)
                        circle.visibility = View.GONE
                        return false
                    }

                }).into(memeImageView)
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
            })


// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
    fun nextmeme(view: View) {
        loadmeme()
    }
    fun sharememe(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey check out this meme $imageurl")
        val chooser=Intent.createChooser(intent,"Share this meme using ...")
        startActivity(chooser)
    }
}