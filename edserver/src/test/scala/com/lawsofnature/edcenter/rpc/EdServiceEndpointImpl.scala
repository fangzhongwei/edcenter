package com.lawsofnature.edcenter.rpc

import javax.inject.Inject

import Ice.Current
import RpcEd.{DecryptResponse, EncryptResponse, _EdServiceEndpointDisp}
import com.lawsofnature.edcenter.service.EdService

/**
  * Created by fangzhongwei on 2016/12/5.
  */
class EdServiceEndpointImpl @Inject()(edService: EdService) extends _EdServiceEndpointDisp {
  override def encrypt(traceId: String, raw: String, current: Current): EncryptResponse = edService.encrypt(traceId, raw)

  override def decrypt(traceId: String, ticket: String, current: Current): DecryptResponse = edService.decrypt(traceId, ticket)
}
