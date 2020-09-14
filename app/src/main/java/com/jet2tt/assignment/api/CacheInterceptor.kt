package com.jet2tt.assignment.api

import com.jet2tt.assignment.Jet2TTApp
import com.jet2tt.assignment.util.ConnectivityUtil
import okhttp3.Interceptor
import okhttp3.Response

class CacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (ConnectivityUtil.isConnected(Jet2TTApp.getInstance()?.getContext())) {
            /*
             *  If there is Internet, get the cache that was stored 5 seconds ago.
             *  If the cache is older than 5 seconds, then discard it,
             *  and indicate an error in fetching the response.
             *  The 'max-age' attribute is responsible for this behavior.
             */
            builder.header("Cache-Control", "public, max-age=" + 5)
        } else {
            /*
             *  If there is no Internet, get the cache that was stored 2 days ago.
             *  If the cache is older than 2 days, then discard it,
             *  and indicate an error in fetching the response.
             *  The 'max-stale' attribute is responsible for this behavior.
             *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
             */
            builder.removeHeader("Pragma")
                .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 2)
        }
        return chain.proceed(builder.build())

    }
}