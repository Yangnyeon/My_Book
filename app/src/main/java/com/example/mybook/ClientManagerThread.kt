package com.example.mybook

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.Socket

class ClientManagerThread : Thread() {
    private var m_socket: Socket? = null
    private val m_ID: String? = null
    private lateinit var myServer: MyServer
    override fun run() {
        super.run()
        try {
            val getIn = BufferedReader(InputStreamReader(m_socket!!.getInputStream()))
            var text: String
            while (true) {
                text = getIn.readLine()
                if (text != null) {
                    for (i in myServer.m_OutputList!!.indices) {
                        myServer.m_OutputList!!.get(i).println(text)
                        myServer.m_OutputList!!.get(i).flush()
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun setSocket(_socket: Socket?) {
        m_socket = _socket
    }
}