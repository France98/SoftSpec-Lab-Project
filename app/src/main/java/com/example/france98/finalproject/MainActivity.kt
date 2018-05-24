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

class MainActivity : AppCompatActivity() {

    private val RSS_LINK = "https%3A%2F%2Fwww.theguardian.com%2Finternational%2Frss"
    private val RSS_to_JSON = "https://api.rss2json.com/v1/api.json?rss_url="

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
        val loadRSSsync = object:AsyncTask<String,String,String>() {
            internal var mDialog = ProgressDialog(this@MainActivity)

            override fun onPreExecute() {
                mDialog.setMessage("Please wait...")
                mDialog.show()
            }

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
                result = http.getHTTPDataHandler(p0[0])
                return result
            }
        }

        val url_get_data = StringBuilder(RSS_to_JSON)
        url_get_data.append(RSS_LINK)
        loadRSSsync.execute(url_get_data.toString())
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
