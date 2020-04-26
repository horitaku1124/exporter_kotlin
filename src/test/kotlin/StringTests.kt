import org.junit.jupiter.api.Test

class StringTests {
  @Test
  fun test1() {
    var regex = "([\\d.]+)% packet loss".toRegex()
    var regex1 = " [\\d.]+/([\\d.]+)".toRegex()
    var str = """PING yahoo.co.jp (183.79.135.206): 56 data bytes
64 bytes from 183.79.135.206: icmp_seq=0 ttl=51 time=60.378 ms

64 bytes from 183.79.135.206: icmp_seq=1 ttl=51 time=62.790 ms

64 bytes from 183.79.135.206: icmp_seq=2 ttl=51 time=61.940 ms

64 bytes from 183.79.135.206: icmp_seq=3 ttl=51 time=284.337 ms

--- yahoo.co.jp ping statistics ---
4 packets transmitted, 4 packets received, 0.0% packet loss
round-trip min/avg/max/stddev = 60.378/117.361/284.337/96.407 ms"""

    var match = regex.find(str)
    if (match != null) {
      var text = match.groups[1]
      println("result=" + text!!.value)
    }
    var match2 = regex1.find(str)
    if (match2 != null) {
      var text = match2.groups[1]
      println("result2=" + text!!.value)
    }
  }
}