package com.example.myapplication.data.parser

import com.example.myapplication.data.model.Song
import com.example.myapplication.data.parser.response.GetTopSongsResponse
import org.xml.sax.Attributes

/**
 * Created by Dhananjay on 10/22/2020
 */
object GetSongsListHandler : BaseDefaultHandler() {

    //TAGS
    private const val ID = "id"
    private const val TITLE = "title"
    private const val NAME = "im:name"
    private const val ARTIST = "im:artist"
    private const val IMAGE = "im:image"
    private const val LINK = "link"
    private const val ENTRY = "entry"

    // Attributes
    private const val IMAGE_HEIGHT = "height"
    private const val LINK_TITLE = "title"
    private const val LINK_HREF = "href"

    // constant values
    private const val LINK_TITLE_VALUE = "Preview"
    private const val IMAGE_HEIGHT_VALUE = 170


//    @PrimaryKey
//    val name: String,
//    val image: String,
//    val duration: String,
//    val link: String,
//    val artist: String

    // local variables
    private var mId: String? = null
    private var songs: ArrayList<Song>? = ArrayList()
    private var mName: String? = null
    private var mArtist: String? = null
    private var mImage: String? = null
    private var mLink: String? = null


    /**
     * In method is called when end of particular tag has been encountered
     * @param key particular tag
     * @value tag value(fetched by opening and closing of tag)
     */
    override fun onEndElement(key: String, attributes: HashMap<String, String>?, value: String?) {
        when (key) {
            ID ->
                if (attributes == null) {
                    mId = value
                }
            NAME ->
                mName = value
            ARTIST ->
                mArtist = value
            IMAGE ->
                if (attributes?.getValue(IMAGE_HEIGHT)?.toInt() == IMAGE_HEIGHT_VALUE) {
                    mImage = value
                }
            LINK ->
                if (attributes != null) {
                    if (attributes.contains(LINK_TITLE) && attributes.getValue(LINK_TITLE) == LINK_TITLE_VALUE) {
                        mLink = attributes.getValue(LINK_HREF)
                    }
                }
            ENTRY -> {
                val song = Song(mName!!, mImage!!, mLink!!, mArtist!!)
                songs?.add(song)
            }
        }
    }

    /**
     * Get handler response in particular response format
     */
    override fun <T : Any> getResponse(): T {

        return GetTopSongsResponse(mId, songs!!) as T
    }
}