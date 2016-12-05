package com.lawsofnature.edcenter.repo

import com.lawsofnature.connection.MySQLDBImpl

/** Entity class storing rows of table TmEncryptedData
  *
  * @param ticket         Database column ticket SqlType(VARCHAR), PrimaryKey, Length(16,true)
  * @param hash           Database column hash SqlType(VARCHAR), Length(64,true)
  * @param encryptType    Database column encrypt_type SqlType(VARCHAR), Length(8,true)
  * @param encryptKey     Database column encrypt_key SqlType(VARCHAR), Length(512,true)
  * @param encryptData    Database column encrypt_data SqlType(VARCHAR), Length(1024,true)
  * @param encryptVersion Database column encrypt_version SqlType(INT), Default(1)
  * @param gmtCreate      Database column gmt_create SqlType(TIMESTAMP) */
case class TmEncryptedDataRow(ticket: String, hash: String, encryptType: String, encryptKey: String, encryptData: String, encryptVersion: Int = 1, gmtCreate: java.sql.Timestamp)

/** Entity class storing rows of table TmEncryptKey
  *
  * @param id             Database column id SqlType(INT), AutoInc, PrimaryKey
  * @param keyType        Database column key_type SqlType(VARCHAR), Length(8,true)
  * @param encryptVersion Database column encrypt_version SqlType(INT)
  * @param keyPath        Database column key_path SqlType(VARCHAR), Length(255,true)
  * @param gmtCreate      Database column gmt_create SqlType(TIMESTAMP) */
case class TmEncryptKeyRow(id: Int, keyType: String, encryptVersion: Int, keyPath: String, gmtCreate: java.sql.Timestamp)

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables extends MySQLDBImpl {

  import profile.api._

  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = TmEncryptedData.schema ++ TmEncryptKey.schema

  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** GetResult implicit for fetching TmEncryptedDataRow objects using plain SQL queries */
  implicit def GetResultTmEncryptedDataRow(implicit e0: GR[String], e1: GR[Int], e2: GR[java.sql.Timestamp]): GR[TmEncryptedDataRow] = GR {
    prs => import prs._
      TmEncryptedDataRow.tupled((<<[String], <<[String], <<[String], <<[String], <<[String], <<[Int], <<[java.sql.Timestamp]))
  }

  /** Table description of table tm_encrypted_data. Objects of this class serve as prototypes for rows in queries. */
  class TmEncryptedData(_tableTag: Tag) extends profile.api.Table[TmEncryptedDataRow](_tableTag, "tm_encrypted_data") {
    def * = (ticket, hash, encryptType, encryptKey, encryptData, encryptVersion, gmtCreate) <> (TmEncryptedDataRow.tupled, TmEncryptedDataRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(ticket), Rep.Some(hash), Rep.Some(encryptType), Rep.Some(encryptKey), Rep.Some(encryptData), Rep.Some(encryptVersion), Rep.Some(gmtCreate)).shaped.<>({ r => import r._; _1.map(_ => TmEncryptedDataRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column ticket SqlType(VARCHAR), PrimaryKey, Length(16,true) */
    val ticket: Rep[String] = column[String]("ticket", O.PrimaryKey, O.Length(16, varying = true))
    /** Database column hash SqlType(VARCHAR), Length(64,true) */
    val hash: Rep[String] = column[String]("hash", O.Length(64, varying = true))
    /** Database column encrypt_type SqlType(VARCHAR), Length(8,true) */
    val encryptType: Rep[String] = column[String]("encrypt_type", O.Length(8, varying = true))
    /** Database column encrypt_key SqlType(VARCHAR), Length(512,true) */
    val encryptKey: Rep[String] = column[String]("encrypt_key", O.Length(512, varying = true))
    /** Database column encrypt_data SqlType(VARCHAR), Length(1024,true) */
    val encryptData: Rep[String] = column[String]("encrypt_data", O.Length(1024, varying = true))
    /** Database column encrypt_version SqlType(INT), Default(1) */
    val encryptVersion: Rep[Int] = column[Int]("encrypt_version", O.Default(1))
    /** Database column gmt_create SqlType(TIMESTAMP) */
    val gmtCreate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("gmt_create")

    /** Uniqueness Index over (hash) (database name uq_ed_hs) */
    val index1 = index("uq_ed_hs", hash, unique = true)
  }

  /** Collection-like TableQuery object for table TmEncryptedData */
  lazy val TmEncryptedData = new TableQuery(tag => new TmEncryptedData(tag))

  /** GetResult implicit for fetching TmEncryptKeyRow objects using plain SQL queries */
  implicit def GetResultTmEncryptKeyRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp]): GR[TmEncryptKeyRow] = GR {
    prs => import prs._
      TmEncryptKeyRow.tupled((<<[Int], <<[String], <<[Int], <<[String], <<[java.sql.Timestamp]))
  }

  /** Table description of table tm_encrypt_key. Objects of this class serve as prototypes for rows in queries. */
  class TmEncryptKey(_tableTag: Tag) extends profile.api.Table[TmEncryptKeyRow](_tableTag, "tm_encrypt_key") {
    def * = (id, keyType, encryptVersion, keyPath, gmtCreate) <> (TmEncryptKeyRow.tupled, TmEncryptKeyRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(keyType), Rep.Some(encryptVersion), Rep.Some(keyPath), Rep.Some(gmtCreate)).shaped.<>({ r => import r._; _1.map(_ => TmEncryptKeyRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column key_type SqlType(VARCHAR), Length(8,true) */
    val keyType: Rep[String] = column[String]("key_type", O.Length(8, varying = true))
    /** Database column encrypt_version SqlType(INT) */
    val encryptVersion: Rep[Int] = column[Int]("encrypt_version")
    /** Database column key_path SqlType(VARCHAR), Length(255,true) */
    val keyPath: Rep[String] = column[String]("key_path", O.Length(255, varying = true))
    /** Database column gmt_create SqlType(TIMESTAMP) */
    val gmtCreate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("gmt_create")
  }

  /** Collection-like TableQuery object for table TmEncryptKey */
  lazy val TmEncryptKey = new TableQuery(tag => new TmEncryptKey(tag))
}
