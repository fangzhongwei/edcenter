import org.apache.commons.codec.digest.DigestUtils

/**
  * Created by fangzhongwei on 2016/12/5.
  */
object ClientTest extends App{

  println("client test")

  println(DigestUtils.sha256Hex("1581126718".getBytes("UTF-8")))
  println(DigestUtils.sha256Hex("1581126718".getBytes("UTF-8")))
  println(DigestUtils.sha256Hex("1581126718".getBytes("UTF-8")))
  println(DigestUtils.sha256Hex("1581126718".getBytes("UTF-8")))
  println(DigestUtils.sha256Hex("fangzhongwei@outlook.com".getBytes("UTF-8")))
  println(DigestUtils.sha256Hex("fangzhongwei@outlook.com".getBytes("UTF-8")))
  println(DigestUtils.sha256Hex("fangzhongwei@outlook.com".getBytes("UTF-8")).length)

}
