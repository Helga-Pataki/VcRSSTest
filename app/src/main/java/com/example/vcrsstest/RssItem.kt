package com.example.vcrsstest

class RssItem {
    var title = ""
    var link = ""
    var pubDate = ""
    var description = ""
    var category = ""
    var imag = ""
    var textContent =""

    override fun toString(): String {
        return "RssItem(title='$title', link='$link', pubDate='$pubDate', description='$description', category='$category', imag= $imag , textContent= $textContent )"
    }

}