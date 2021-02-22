package com.qrcode.barcode.qrscanner.qrcodegenerator.dubizzle_androidtakehomeproject

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.qrcode.barcode.qrscanner.qrcodegenerator.dubizzle_androidtakehomeproject.Adapter.ReviewAdapter
import com.qrcode.barcode.qrscanner.qrcodegenerator.dubizzle_androidtakehomeproject.Model.QuestionsModel
import com.qrcode.barcode.qrscanner.qrcodegenerator.dubizzle_androidtakehomeproject.Server.MySingleton
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    val url: String =
        "https://ey3f2y0nre.execute-api.us-east-1.amazonaws.com/default/dynamodb-writer"
    var question_list: ArrayList<QuestionsModel>? = null
    var reviewAdapter: ReviewAdapter? = null
    var recycler_ans: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_ans = findViewById(R.id.recycler_ans)
        getlisting()




    }


    private fun getlisting() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loading_dialog)



        dialog.show()

        val RegistrationRequest: StringRequest = object : StringRequest(Method.GET,
            url,
            Response.Listener
            { response ->
                try {

                    dialog.dismiss()

                    val objects = JSONObject(response)
                    question_list = ArrayList<QuestionsModel>()
                    val results = objects.getJSONArray("results")
                    for (i in 0 until results.length()) {

                        val jsonObject = results.getJSONObject(i)

                        val image_ids: JSONArray = jsonObject.getJSONArray("image_ids")
                        var iddata: String? = ""
                        for (k in 0 until image_ids.length()) {
                            iddata = image_ids.getString(k)

                        }
                        val image_urls: JSONArray = jsonObject.getJSONArray("image_urls")
                        var img_url: String?=""
                        for (k in 0 until image_urls.length()) {
                            img_url = image_urls.getString(k)

                        }
                        val image_urls_thumbnails: JSONArray =
                            jsonObject.getJSONArray("image_urls_thumbnails")
                        var img_thumbnail: String? = ""
                        for (k in 0 until image_urls_thumbnails.length()) {
                            img_thumbnail = image_urls_thumbnails.getString(k)

                        }


//                            val correct = jsonObject.getInt("correct")

                        question_list?.add(
                            QuestionsModel(
                                jsonObject.getString("created_at"),
                                jsonObject.getString("price"),
                                jsonObject.getString("name"),
                                jsonObject.getString("uid"),
                                iddata, img_url, img_thumbnail
                            )
                        )

                    }


                    reviewAdapter = ReviewAdapter(this@MainActivity, question_list, applicationContext)
                    val layoutManager: RecyclerView.LayoutManager =
                        GridLayoutManager(applicationContext, 1, RecyclerView.VERTICAL, false)
                    recycler_ans?.layoutManager = layoutManager
                    recycler_ans?.adapter = reviewAdapter


                    //                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                dialog.dismiss()
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    dialog.dismiss()
                    var message: String? = null
                    when (volleyError) {
                        is NetworkError -> {
                            message = "Cannot connect to Internet...Please check your connection!"
                        }
                        is ServerError -> {
                            message =
                                "The server could not be found. Please try again after some time!!"
                        }
                        is AuthFailureError -> {
                            message = "Cannot connect to Internet...Please check your connection!"
                        }
                        is ParseError -> {
                            message = "Parsing error! Please try again after some time!!"
                        }
                        is NoConnectionError -> {
                            message = "Cannot connect to Internet...Please check your connection!"
                        }
                        is TimeoutError -> {
                            message = "Connection TimeOut! Please check your internet connection."
                        }
                    }
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                }
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val header: MutableMap<String, String> = HashMap()
                header["x-rapidapi-key"] = "4b0a7a8e58msh8689a173291538bp14625fjsn1337b5d8ce63"
                header["x-rapidapi-host"] = "twinword-word-association-quiz.p.rapidapi.com"
                return header


            }
        }
        RegistrationRequest.retryPolicy = DefaultRetryPolicy(
            25000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        MySingleton.getInstance(this).addToRequestQueue(RegistrationRequest)
    }


}