package com.lawsofnature.edcenter.service

import java.sql.Timestamp
import javax.inject.Inject

import RpcEd.{DecryptResponse, EncryptResponse}
import com.lawsofnature.common.edecrypt.rsa.{EDecryptUtils, RSAHexUtils}
import com.lawsofnature.common.exception.ErrorCode
import com.lawsofnature.common.helper.TokenHelper
import com.lawsofnature.edcenter.repo.{EDecryptRepository, TmEncryptedDataRow}
import com.typesafe.config.ConfigFactory
import org.apache.commons.codec.digest.DigestUtils

/**
  * Created by fangzhongwei on 2016/12/5.
  */
trait EdService {
  def encrypt(traceId: String, raw: String): EncryptResponse

  def decrypt(traceId: String, ticket: String): DecryptResponse
}

class EdServiceImpl @Inject()(eDecryptRepository: EDecryptRepository) extends EdService {
  var PRE_ = "T"
  var encryptTypeRsa = "RSA-3DES"
  var defaultCharset = "UTF-8"
  var rasPublicKey = ConfigFactory.load().getString("edcrypt.ras.public.key")
  var rasPrivateKey = ConfigFactory.load().getString("edcrypt.ras.private.key")

  override def encrypt(traceId: String, raw: String): EncryptResponse = {
    val threeDesKey: String = TokenHelper.generateHexToken(16)
    val encryptedThreeDesKey: String = RSAHexUtils.encryptByPublic(threeDesKey, rasPublicKey)
    val encryptedData: String = EDecryptUtils.encrypt(raw, encryptedThreeDesKey, rasPrivateKey)
    val ticket: String = eDecryptRepository.getNextTicket(PRE_)
    eDecryptRepository.saveEncryptedData(TmEncryptedDataRow(ticket, DigestUtils.sha256Hex(raw.getBytes(defaultCharset)), encryptTypeRsa, encryptedThreeDesKey, encryptedData, 1, new Timestamp(System.currentTimeMillis())))
    new EncryptResponse(true, 0, ticket)
  }

  override def decrypt(traceId: String, ticket: String): DecryptResponse = {
    val encryptedDataRow: TmEncryptedDataRow = eDecryptRepository.getEncryptedData(ticket)
    encryptedDataRow == null match {
      case false => new DecryptResponse(true, 0, EDecryptUtils.decrypt(encryptedDataRow.encryptData, encryptedDataRow.encryptKey, rasPrivateKey))
      case true => new DecryptResponse(false, ErrorCode.EC_ED_TICKET_NOT_EXISTS.getCode, "")
    }
  }
}
