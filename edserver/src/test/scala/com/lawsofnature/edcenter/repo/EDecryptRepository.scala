package com.lawsofnature.edcenter.repo

import com.lawsofnature.connection.{DBComponent, MySQLDBImpl}

import scala.concurrent.{Await, Future}
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

  def getEncryptedData(ticket: String): Option[TmEncryptedDataRow] =
    Await.result(db.run {
      TmEncryptedData.filter(_.ticket === ticket).result.headOption
    }, Duration.Inf)

  var index = -1

  def getNextTicket(pre:String): String = {
    index = index + 1
    val sequenceName = "member_id_" + index % 3
    new StringBuilder(pre).append(Await.result(db.run(sql"""select nextval($sequenceName)""".as[(Long)]), Duration.Inf).head).toString()
  }

}

class EDecryptRepositoryImpl extends EDecryptRepository with MySQLDBImpl

