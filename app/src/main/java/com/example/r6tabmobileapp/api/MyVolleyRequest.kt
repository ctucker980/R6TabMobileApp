package com.example.r6tabmobileapp.api
import android.content.Context
import android.graphics.Bitmap
import androidx.core.util.lruCache
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

class MyVolleyRequest{
    private var mRequestQueue: RequestQueue?=null
    private var context: Context?=null
    private var iVolley:IVolley?=null
    var imageLoader: ImageLoader?=null

    val requestQueue:RequestQueue
    get() {
        if(mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(context!!.applicationContext)
        return mRequestQueue!!
    }

    private constructor(context: Context, iVolley: IVolley) {
        this.context = context
        this.iVolley = iVolley
        mRequestQueue = requestQueue
        this.imageLoader = ImageLoader(mRequestQueue,object : ImageLoader.ImageCache {
            private val mCache = lruCache<String, Bitmap> (maxSize = 10)
            override fun getBitmap(url: String?): Bitmap {
                return mCache.get(url)
            }

            override fun putBitmap(url: String?, bitmap: Bitmap?) {
                mCache.put(url, bitmap)
            }

        })
    }

    private constructor(context: Context) {
        this.context = context
        mRequestQueue = requestQueue
        this.imageLoader = ImageLoader(mRequestQueue,object : ImageLoader.ImageCache {
            private val mCache = lruCache<String, Bitmap> (maxSize = 10)
            override fun getBitmap(url: String?): Bitmap {
                return mCache.get(url)
            }

            override fun putBitmap(url: String?, bitmap: Bitmap?) {
                mCache.put(url, bitmap)
            }

        })
    }


}