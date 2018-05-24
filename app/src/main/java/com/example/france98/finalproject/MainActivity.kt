package com.example.france98.finalproject

import android.app.ProgressDialog
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.france98.finalproject.Adapter.FeedAdapter
import com.example.france98.finalproject.Common.HTTPDataHandler
import com.example.france98.finalproject.Model.RSSObject
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val RSS_to_JSON = "https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Froyalroadl.com%2Fsyndication%2F10286"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.france98.finalproject.R.layout.activity_main)

        toolbar.title="Update"
        setSupportActionBar(toolbar)

        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL , false)
        recyclerview.layoutManager = linearLayoutManager

        loadRSS()

    }

    private fun loadRSS(){
        object:AsyncTask<String,Void,String>() {
            internal var mDialog = ProgressDialog(this@MainActivity)


            override fun onPostExecute(result: String?) {
                mDialog.dismiss()
                var rssObject:RSSObject
                rssObject = Gson().fromJson<RSSObject>(result,RSSObject::class.java!!)
                val adapter = FeedAdapter(rssObject ,baseContext)
                recyclerview.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun doInBackground(vararg p0: String?): String {
                val result:String
                val http = HTTPDataHandler()
                result = http.getHTTPDataHandler(RSS_to_JSON)
                return result
            }
        }.execute()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.title.equals("Refresh"))
            loadRSS()
        return true
    }
}
