package com.github.horitaku1124.kotlin.exporter

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.nio.file.Files
import java.nio.file.Path

class ExporterMain {
  companion object {
    private const val EXPOSE_PORT = 8111
    lateinit var collector: Collector
    @JvmStatic
    fun main(args: Array<String>) {
      val propFile = args[0]
      var conf = loadProp(propFile)

      collector = Collector()
      collector.start()
      val server = HttpServer.create(InetSocketAddress(EXPOSE_PORT), 0)
      val context = server.createContext("/")
      context.setHandler(this::handleRequest)
      println("started")
      server.start()
    }

    private fun loadProp(fileName: String): Map<String, String> {
      val conf = hashMapOf<String, String>()
      val lines = Files.readAllLines(Path.of(fileName))
      for (line in lines) {
        if (line == null || line.isBlank()) continue
        val pair = line.split("=")
        if (pair.size == 2) {
          conf[pair[0]] = pair[1]
        }
      }
      return conf
    }

    private fun handleRequest(exchange: HttpExchange) {
      var body = ""
      body += "packet_loss\t" + collector.packetLoss + "\n"
      body += "average_time\t" + collector.averageTime + "\n"
      val response = body.toByteArray()
      exchange.sendResponseHeaders(200, response.size.toLong()) //response code and length
      val os = exchange.responseBody
      os.write(response)
      os.flush()
      os.close()
    }
  }
}