package com.example.repconnectjetpackcompose.models

import java.io.Serializable

data class Manufacturer(
    var Address : String? = null,
    var Blurb : String? = null,
    var City : String? = null,
    var ContactId : Int? = null,
    var ContactName : String? = null,
    var DocumentCount : Int? = null,
    var EmailAddress : String? = null,
    var Keywords : String? = null,
    var LogoUrl : String? = null,
    var MainFax : String? = null,
    var MainPhone : String? = null,
    var RepCount : Int? = null,
    var State : String? = null,
    var WebsiteUrl : String? = null,
    var Zip : String? = null,



    ) : Serializable
