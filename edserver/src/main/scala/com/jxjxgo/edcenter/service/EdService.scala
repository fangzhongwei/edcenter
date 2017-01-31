package com.jxjxgo.edcenter.service

import java.sql.Timestamp
import javax.inject.Inject

import com.jxjxgo.common.helper.TokenHelper
import com.jxjxgo.edcenter.repo.{EDecryptRepository, TmEncryptedDataRow}
import com.jxjxgo.edcenter.rpc.domain.{DecryptResponse, EncryptResponse}
import com.lawsofnature.common.edecrypt.rsa.{EDecryptUtils, RSAHexUtils}
import com.lawsofnature.common.exception.ErrorCode
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
    val sha: String = DigestUtils.sha256Hex(raw.getBytes(defaultCharset))

    val existedEncryptedDataRow: TmEncryptedDataRow = eDecryptRepository.getEncryptedDataBySha(sha)
    existedEncryptedDataRow == null match {
      case true =>
        eDecryptRepository.saveEncryptedData(TmEncryptedDataRow(ticket, sha, encryptTypeRsa, encryptedThreeDesKey, encryptedData, 1, new Timestamp(System.currentTimeMillis())))
        EncryptResponse("0", ticket)
      case false =>
        EncryptResponse("0", existedEncryptedDataRow.ticket)
    }
  }

  override def decrypt(traceId: String, ticket: String): DecryptResponse = {
    val encryptedDataRow: TmEncryptedDataRow = eDecryptRepository.getEncryptedData(ticket)
    encryptedDataRow == null match {
      case false => DecryptResponse("0", EDecryptUtils.decrypt(encryptedDataRow.encryptData, encryptedDataRow.encryptKey, rasPrivateKey))
      case true => DecryptResponse(ErrorCode.EC_ED_TICKET_NOT_EXISTS.getCode, "")
    }
  }
}
