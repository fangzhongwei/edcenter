package com.jxjxgo.edcenter.test

import com.jxjxgo.edcenter.rpc.domain.EdServiceEndpoint
import com.twitter.finagle.Thrift
import com.twitter.util.{Await, Future}

/**
  * Created by fangzhongwei on 2017/1/31.
  */
object EdTest {

  def main(args: Array[String]): Unit = {
    val endpoint: EdServiceEndpoint[Future] = Thrift.client.newIface[EdServiceEndpoint[Future]]("127.0.0.1:8801")
    println(Await.result(endpoint.decrypt("qqq", "T23")))
  }

}
