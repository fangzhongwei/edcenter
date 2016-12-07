package com.lawsofnature.edcenter.repo

import com.lawsofnatrue.common.cache.anno.ServiceCache
import com.lawsofnatrue.common.cache.enumeration.CacheMethod
import com.lawsofnature.connection.{DBComponent, MySQLDBImpl}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by fangzhongwei on 2016/12/5.
  */
trait EDecryptRepository extends Tables {
  this: DBComponent =>

  import profile.api._

  def saveEncryptedData(tmEncryptedDataRow: TmEncryptedDataRow): Int =
    Await.result(db.run {
      TmEncryptedData += tmEncryptedDataRow
    }, Duration.Inf)

  @ServiceCache(method = CacheMethod.SELECT, keyDir = "e-t:", expireSeconds = -1)
  def getEncryptedData(ticket: String): TmEncryptedDataRow =
    Await.result(db.run {
      TmEncryptedData.filter(_.ticket === ticket).result.headOption
    }, Duration.Inf) match {
      case Some(raw) => raw
      case None => null
    }

  @ServiceCache(method = CacheMethod.SELECT, keyDir = "e-s:", expireSeconds = -1)
  def getEncryptedDataBySha(sha: String): TmEncryptedDataRow =
    Await.result(db.run {
      TmEncryptedData.filter(_.hash === sha).result.headOption
    }, Duration.Inf) match {
      case Some(raw) => raw
      case None => null
    }

  var index = -1

  def getNextTicket(pre: String): String = {
    index = index + 1
    val sequenceName = new StringBuilder("ticket_id_").append(index % 3).toString()
    new StringBuilder(pre).append(Await.result(db.run(sql"""select nextval($sequenceName)""".as[(Long)]), Duration.Inf).head).toString()
  }
}

class EDecryptRepositoryImpl extends EDecryptRepository with MySQLDBImpl

