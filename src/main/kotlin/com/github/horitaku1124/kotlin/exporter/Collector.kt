package com.github.horitaku1124.kotlin.exporter

class Collector: java.lang.Thread() {
  var packetLossPattern = "([\\d.]+)% packet loss".toRegex()
  var averageTimePattern = " [\\d.]+/([\\d.]+)".toRegex()

  var packetLoss = 0.0
  var averageTime = 0.0
  override fun run() {
    val command = listOf("/bin/bash", "-c", "ping yahoo.co.jp -c 4 -i 3")

    while (true) {
      var child = ProcessBuilder(command)
        .start()

      val stdin = child.inputStream
      val stderr = child.errorStream

      var outBuff = StringBuffer()
      var buff = ByteArray(1024 * 1024)
      while(child.isAlive) {
        var notRead = true
        if (stdin.available() > 0) {
          var len = stdin.read(buff)
          outBuff.append(String(buff, 0, len))
        }
        if (stderr.available() > 0) {
          var len = stderr.read(buff)
          outBuff.append(String(buff, 0, len))
        }
        if (notRead) {
          sleep(100)
        }
      }
      if (stdin.available() > 0) {
        var len = stdin.read(buff)
        outBuff.append(String(buff, 0, len))
      }
      if (stderr.available() > 0) {
        var len = stderr.read(buff)
        outBuff.append(String(buff, 0, len))
      }

      stdin.close()
      stderr.close()
      child.destroy()

//    println("Result:" + outBuff.toString())

      var resultText = outBuff.toString()

      var match = packetLossPattern.find(resultText)
      if (match != null) {
        var text = match.groups[1]
        packetLoss = text!!.value.toDouble()
        print("packetLoss=" + packetLoss)
      }
      print(" ")
      var match2 = averageTimePattern.find(resultText)
      if (match2 != null) {
        var text = match2.groups[1]
        averageTime = text!!.value.toDouble()
        print("averageTime=" + averageTime)
      }
      println()
      sleep(1000)
    }
  }
}