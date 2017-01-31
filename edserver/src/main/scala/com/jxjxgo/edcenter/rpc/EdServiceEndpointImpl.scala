package com.jxjxgo.edcenter.rpc

import javax.inject.Inject

import com.jxjxgo.edcenter.service.EdService
import com.twitter.util.Future
import com.jxjxgo.edcenter.rpc.domain.{DecryptResponse, EdServiceEndpoint, EncryptResponse}

/**
  * Created by fangzhongwei on 2016/12/5.
  */
class EdServiceEndpointImpl @Inject()(edService: EdService) extends EdServiceEndpoint[Future] {
  override def encrypt(traceId: String, raw: String): Future[EncryptResponse] = Future.value(edService.encrypt(traceId, raw))

  override def decrypt(traceId: String, ticket: String): Future[DecryptResponse] = Future.value(edService.decrypt(traceId, ticket))
}
