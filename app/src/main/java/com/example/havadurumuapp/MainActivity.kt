package com.example.havadurumuapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       /** var request = StringRequest(Request.Method.GET,"http://google.com", object:Response.Listener<String>{

            //android 9'dan itibaren http'sitelere erişim vermiyor. Bunu önlemek için androidManifest.xml de,
            //android:usesCleartextTraffic="true" komutunu ekledik.

            override fun onResponse(response: String?) {
                Toast.makeText(this@MainActivity,"CEVAP: "+response,Toast.LENGTH_LONG).show()

            }
        },object :Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {

            }
        })*/
val url ="https://api.openweathermap.org/data/2.5/weather?lat=40.572707&lon=32.495493&appid=74f394c9ef283ff609313b0688ad73fc&lang=tr&units=metric"
val havaDurumuObjeRequest= JsonObjectRequest(Request.Method.GET,url,null,object:Response.Listener<JSONObject>{
    override fun onResponse(response: JSONObject?) {
        //Toast.makeText(this@MainActivity,response.toString(),Toast.LENGTH_LONG).show()

        var main =response?.getJSONObject("main")
        var sicaklik= main?.getString("temp")
        var sehirisim=response?.getString("name")
        var weather=response?.getJSONArray("weather")
        var havadurumuAciklama= weather?.getJSONObject(0)!!.getString("description")
        var icon= weather?.getJSONObject(0)!!.getString("icon")
        tvSehir.text =sehirisim
        tvSicaklik.text=sicaklik
        tvhavaaciklama.text=havadurumuAciklama
        var resimDosyaAdi=resources.getIdentifier("icon_" +icon.sonKarakteriSil(),"drawable",packageName)
        imghavadurumu.setImageResource(resimDosyaAdi)
        Log.e("deneme",sicaklik + " "+ sehirisim + " "+ havadurumuAciklama + " "+icon)

    }


},object :Response.ErrorListener{
    override fun onErrorResponse(error: VolleyError?) {

    }
})


        MySingleton.getInstance(this).addToRequestQueue(havaDurumuObjeRequest)
    }
}

private fun String.sonKarakteriSil(): String? {
    return this.substring(0,this.length-1)

}
