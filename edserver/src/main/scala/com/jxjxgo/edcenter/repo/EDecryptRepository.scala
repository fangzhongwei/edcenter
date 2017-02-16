package com.jxjxgo.edcenter.repo

import com.jxjxgo.common.cache.anno.{CacheKey, ServiceCache}
import com.jxjxgo.common.cache.enumeration.CacheMethod
import com.jxjxgo.edcenter.domain.cache.encrypteddata.EncryptedData
import com.jxjxgo.mysql.connection.{DBComponent, DBImpl}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by fangzhongwei on 2016/12/5.
  */
trait EDecryptRepository extends Tables {
  this: DBComponent =>

  import profile.api._

  implicit def convert(r: TmEncryptedDataRow): EncryptedData = {
    EncryptedData(r.ticket, r.hash, r.encryptType, r.encryptKey, r.encryptData, r.encryptVersion)
  }

  def saveEncryptedData(tmEncryptedDataRow: TmEncryptedDataRow): Int =
    Await.result(db.run {
      TmEncryptedData += tmEncryptedDataRow
    }, Duration.Inf)

  @ServiceCache(method = CacheMethod.SELECT, keyDir = "e-t:", expireSeconds = 2592000)
  def getEncryptedData(@CacheKey ticket: String): EncryptedData =
    Await.result(db.run {
      TmEncryptedData.filter(_.ticket === ticket).result.headOption
    }, Duration.Inf) match {
      case Some(raw) => raw
      case None => null
    }

  @ServiceCache(method = CacheMethod.SELECT, keyDir = "e-s:", expireSeconds = 2592000) //90 days
  def getEncryptedDataBySha(@CacheKey sha: String): EncryptedData =
    Await.result(db.run {
      TmEncryptedData.filter(_.hash === sha).result.headOption
    }, Duration.Inf) match {
      case Some(raw) => raw
      case None => null
    }

  def getNextTicket(pre: String): String = {
    new StringBuilder(pre).append(Await.result(db.run(sql"""SELECT nextval('seq_ticket_id')""".as[(Long)]), Duration.Inf).head).toString()
  }
}

class EDecryptRepositoryImpl extends EDecryptRepository with DBComponent

