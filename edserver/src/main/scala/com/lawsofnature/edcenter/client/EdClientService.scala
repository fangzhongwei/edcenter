package com.lawsofnature.edcenter.client

import javax.inject.{Inject, Named}

import RpcEd.{DecryptResponse, EdServiceEndpointPrx, EdServiceEndpointPrxHelper, EncryptResponse}
import com.lawsofnatrue.common.ice.IcePrxFactory
import org.slf4j.LoggerFactory

/**
  * Created by fangzhongwei on 2016/12/5.
  */
trait EdClientService {
  def initClient

  def encrypt(traceId: String, raw: String): EncryptResponse

  def decrypt(traceId: String, raw: String): DecryptResponse
}

class EdClientServiceImpl @Inject()(@Named("edcenter.ice.client.init.config") iceInitConfig: String,
                                    @Named("edcenter.ice.client.init.size") iceInitSizeConfig: String,
                                    @Named("edcenter.ice.client.init.size-max") iceInitSizeMaxConfig: String,
                                    @Named("edcenter.ice.client.init.size-warn") iceInitSizeWarnConfig: String,
                                    @Named("edcenter.ice.client.proxy.config") proxyConfig: String,
                                    icePrxFactory: IcePrxFactory) extends EdClientService {
  val logger = LoggerFactory.getLogger(this.getClass)
  var edServiceEndpoint: EdServiceEndpointPrx = _

  override def initClient = edServiceEndpoint = icePrxFactory.make[EdServiceEndpointPrx](Array[String](iceInitConfig, iceInitSizeConfig, iceInitSizeMaxConfig, iceInitSizeWarnConfig), proxyConfig, EdServiceEndpointPrxHelper.checkedCast)

  override def encrypt(traceId: String, raw: String) = edServiceEndpoint.encrypt(traceId, raw)

  override def decrypt(traceId: String, ticket: String) = edServiceEndpoint.decrypt(traceId, ticket)
}
