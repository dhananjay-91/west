package com.example.myapplication.data.parser

import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by Dhananjay on 10/21/2020
 */
abstract class BaseDefaultHandler : DefaultHandler() {

    private var current = false
    private var currentValue: String? = null
    private val mStack = Stack<HashMap<String, String>?>()

    @Throws(SAXException::class)
    override fun startElement(
        uri: String?,
        userData: String,
        qName: String?,
        attributes: Attributes?
    ) {
        current = true


        if (attributes?.length!! > 0) {
            val mAttMap = HashMap<String, String>()
            for (i in 0 until attributes.length) {
                mAttMap[attributes.getQName(i)] = attributes.getValue(i)
            }
            mStack.push(mAttMap)
        } else {
            mStack.push(null)
        }
    }

    @Throws(SAXException::class)
    override fun endElement(
        uri: String?,
        localName: String?,
        qName: String?
    ) {
        current = false
        if (localName != null && currentValue != null) {
            onEndElement(qName!!, mStack.pop(), currentValue)
        } else {
            mStack.pop()
        }

    }

    @Throws(SAXException::class)
    override fun characters(ch: CharArray?, start: Int, length: Int) {
        if (current) {
            currentValue = String(ch!!, start, length)
            current = false
        }
    }


    /**
     * In method is called when end of particular tag has been encountered
     * @param key particular tag
     * @value tag value(fetched by opening and closing of tag)
     */
    abstract fun onEndElement(key: String, attributes: HashMap<String, String>?, value: String?)

    /**
     * Get handler response in particular response format
     */
    abstract fun <T : Any> getResponse(): T
}