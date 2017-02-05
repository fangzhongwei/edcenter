package com.jxjxgo.edcenter.service

import java.sql.Timestamp
import javax.inject.Inject

import com.jxjxgo.common.edecrypt.EDecryptUtils
import com.jxjxgo.common.edecrypt.rsa.RSAHexUtils
import com.jxjxgo.common.exception.ErrorCode
import com.jxjxgo.common.helper.TokenHelper
import com.jxjxgo.edcenter.domain.cache.encrypteddata.EncryptedData
import com.jxjxgo.edcenter.repo.{EDecryptRepository, TmEncryptedDataRow}
import com.jxjxgo.edcenter.rpc.domain.{DecryptResponse, EncryptResponse}
import com.typesafe.config.ConfigFactory
import org.apache.commons.codec.digest.DigestUtils

/**
  * Created by fangzhongwei on 2016/12/5.
  */
trait  EdService {
  def encrypt(traceId: String, raw: String): EncryptResponse

  def decrypt(traceId: String, ticket: String): DecryptResponse
}

class EdServiceImpl @Inject()(eDecryptRepository: EDecryptRepository) extends EdService {
  private[this] val PRE_ = "T"
  private[this] val encryptTypeRsa = "RSA-3DES"
  private[this] val defaultCharset = "UTF-8"
  private[this] val rasPublicKey = ConfigFactory.load().getString("edcrypt.ras.public.key")
  private[this] val rasPrivateKey = ConfigFactory.load().getString("edcrypt.ras.private.key")

  override def encrypt(traceId: String, raw: String): EncryptResponse = {
    val threeDesKey: String = TokenHelper.generateHexToken(16)
    val encryptedThreeDesKey: String = RSAHexUtils.encryptByPublic(threeDesKey, rasPublicKey)
    val encryptedData: String = EDecryptUtils.encrypt(raw, encryptedThreeDesKey, rasPrivateKey)
    val ticket: String = eDecryptRepository.getNextTicket(PRE_)
    val sha: String = DigestUtils.sha256Hex(raw.getBytes(defaultCharset))

    val existedEncryptedDataRow: EncryptedData = eDecryptRepository.getEncryptedDataBySha(sha)
    existedEncryptedDataRow == null match {
      case true =>
        eDecryptRepository.saveEncryptedData(TmEncryptedDataRow(ticket, sha, encryptTypeRsa, encryptedThreeDesKey, encryptedData, 1, new Timestamp(System.currentTimeMillis())))
        EncryptResponse("0", ticket)
      case false =>
        EncryptResponse("0", existedEncryptedDataRow.ticket)
    }
  }

  override def decrypt(traceId: String, ticket: String): DecryptResponse = {
    val encryptedDataRow: EncryptedData = eDecryptRepository.getEncryptedData(ticket)
    encryptedDataRow == null match {
      case false => DecryptResponse("0", EDecryptUtils.decrypt(encryptedDataRow.encryptData, encryptedDataRow.encryptKey, rasPrivateKey))
      case true => DecryptResponse(ErrorCode.EC_ED_TICKET_NOT_EXISTS.getCode, "")
    }
  }
}
