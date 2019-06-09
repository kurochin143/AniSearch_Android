package com.example.israel.anisearch.anilist_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Staffs : Page() {

    @SerializedName("staff")
    @Expose
    var staffs: ArrayList<Staff>? = null

}