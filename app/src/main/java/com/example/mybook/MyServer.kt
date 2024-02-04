package com.example.mybook

import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

public class MyServer {
    var m_OutputList: ArrayList<PrintWriter>? = null

    fun main(args: Array<String>) {
        m_OutputList = ArrayList()
        try {
            val s_socket = ServerSocket(8888)
            while (true) {
                val c_socket: Socket = s_socket.accept()
                val c_thread = ClientManagerThread()
                c_thread.setSocket(c_socket)
                m_OutputList!!.add(PrintWriter(c_socket.getOutputStream()))
                println(m_OutputList!!.size)
                c_thread.start()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}