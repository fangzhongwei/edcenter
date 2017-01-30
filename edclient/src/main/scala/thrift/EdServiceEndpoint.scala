/**
 * Generated by Scrooge
 *   version: 4.5.0
 *   rev: 014664de600267b36809bbc85225e26aec286216
 *   built at: 20160203-205352
 */
package thrift

import com.twitter.scrooge.{
  LazyTProtocol,
  TFieldBlob, ThriftService, ThriftStruct,
  ThriftStructCodec, ThriftStructCodec3,
  ThriftStructFieldInfo, ThriftResponse, ThriftUtil, ToThriftService}
import com.twitter.finagle.{service => ctfs}
import com.twitter.finagle.thrift.{Protocols, ThriftClientRequest, ThriftServiceIface}
import com.twitter.util.Future
import java.nio.ByteBuffer
import java.util.Arrays
import org.apache.thrift.protocol._
import org.apache.thrift.transport.TTransport
import org.apache.thrift.TApplicationException
import org.apache.thrift.transport.TMemoryBuffer
import scala.collection.immutable.{Map => immutable$Map}
import scala.collection.mutable.{
  Builder,
  ArrayBuffer => mutable$ArrayBuffer, Buffer => mutable$Buffer,
  HashMap => mutable$HashMap, HashSet => mutable$HashSet}
import scala.collection.{Map, Set}
import scala.language.higherKinds


@javax.annotation.Generated(value = Array("com.twitter.scrooge.Compiler"))
trait EdServiceEndpoint[+MM[_]] extends ThriftService {
  
  def encrypt(traceId: String, raw: String): MM[thrift.EncryptResponse]
  
  def decrypt(traceId: String, ticket: String): MM[thrift.DecryptResponse]
}



object EdServiceEndpoint { self =>

  case class ServiceIface(
      encrypt : com.twitter.finagle.Service[self.Encrypt.Args, self.Encrypt.Result],
      decrypt : com.twitter.finagle.Service[self.Decrypt.Args, self.Decrypt.Result]
  ) extends BaseServiceIface

  // This is needed to support service inheritance.
  trait BaseServiceIface extends ToThriftService {
    def encrypt : com.twitter.finagle.Service[self.Encrypt.Args, self.Encrypt.Result]
    def decrypt : com.twitter.finagle.Service[self.Decrypt.Args, self.Decrypt.Result]

    override def toThriftService: ThriftService = new MethodIface(this)
  }

  implicit object ServiceIfaceBuilder
    extends com.twitter.finagle.thrift.ServiceIfaceBuilder[ServiceIface] {
      def newServiceIface(
        binaryService: com.twitter.finagle.Service[ThriftClientRequest, Array[Byte]],
        pf: TProtocolFactory = Protocols.binaryFactory(),
        stats: com.twitter.finagle.stats.StatsReceiver
      ): ServiceIface =
        new ServiceIface(
          encrypt = ThriftServiceIface(self.Encrypt, binaryService, pf, stats),
          decrypt = ThriftServiceIface(self.Decrypt, binaryService, pf, stats)
      )
  }

  class MethodIface(serviceIface: BaseServiceIface)
    extends EdServiceEndpoint[Future] {
    private[this] val __encrypt_service =
      ThriftServiceIface.resultFilter(self.Encrypt) andThen serviceIface.encrypt
    def encrypt(traceId: String, raw: String): Future[thrift.EncryptResponse] =
      __encrypt_service(self.Encrypt.Args(traceId, raw))
    private[this] val __decrypt_service =
      ThriftServiceIface.resultFilter(self.Decrypt) andThen serviceIface.decrypt
    def decrypt(traceId: String, ticket: String): Future[thrift.DecryptResponse] =
      __decrypt_service(self.Decrypt.Args(traceId, ticket))
  }

  implicit object MethodIfaceBuilder
    extends com.twitter.finagle.thrift.MethodIfaceBuilder[ServiceIface, EdServiceEndpoint[Future]] {
    def newMethodIface(serviceIface: ServiceIface): EdServiceEndpoint[Future] =
      new MethodIface(serviceIface)
  }

  object Encrypt extends com.twitter.scrooge.ThriftMethod {
    
    object Args extends ThriftStructCodec3[Args] {
      private val NoPassthroughFields = immutable$Map.empty[Short, TFieldBlob]
      val Struct = new TStruct("encrypt_args")
      val TraceIdField = new TField("traceId", TType.STRING, 1)
      val TraceIdFieldManifest = implicitly[Manifest[String]]
      val RawField = new TField("raw", TType.STRING, 2)
      val RawFieldManifest = implicitly[Manifest[String]]
    
      /**
       * Field information in declaration order.
       */
      lazy val fieldInfos: scala.List[ThriftStructFieldInfo] = scala.List[ThriftStructFieldInfo](
        new ThriftStructFieldInfo(
          TraceIdField,
          false,
          false,
          TraceIdFieldManifest,
          _root_.scala.None,
          _root_.scala.None,
          immutable$Map.empty[String, String],
          immutable$Map.empty[String, String]
        ),
        new ThriftStructFieldInfo(
          RawField,
          false,
          false,
          RawFieldManifest,
          _root_.scala.None,
          _root_.scala.None,
          immutable$Map.empty[String, String],
          immutable$Map.empty[String, String]
        )
      )
    
      lazy val structAnnotations: immutable$Map[String, String] =
        immutable$Map.empty[String, String]
    
      /**
       * Checks that all required fields are non-null.
       */
      def validate(_item: Args): Unit = {
      }
    
      def withoutPassthroughFields(original: Args): Args =
        new Args(
          traceId =
            {
              val field = original.traceId
              field
            },
          raw =
            {
              val field = original.raw
              field
            }
        )
    
      override def encode(_item: Args, _oproto: TProtocol): Unit = {
        _item.write(_oproto)
      }
    
      override def decode(_iprot: TProtocol): Args = {
        var traceId: String = null
        var raw: String = null
        var _passthroughFields: Builder[(Short, TFieldBlob), immutable$Map[Short, TFieldBlob]] = null
        var _done = false
    
        _iprot.readStructBegin()
        while (!_done) {
          val _field = _iprot.readFieldBegin()
          if (_field.`type` == TType.STOP) {
            _done = true
          } else {
            _field.id match {
              case 1 =>
                _field.`type` match {
                  case TType.STRING =>
                    traceId = readTraceIdValue(_iprot)
                  case _actualType =>
                    val _expectedType = TType.STRING
                    throw new TProtocolException(
                      "Received wrong type for field 'traceId' (expected=%s, actual=%s).".format(
                        ttypeToString(_expectedType),
                        ttypeToString(_actualType)
                      )
                    )
                }
              case 2 =>
                _field.`type` match {
                  case TType.STRING =>
                    raw = readRawValue(_iprot)
                  case _actualType =>
                    val _expectedType = TType.STRING
                    throw new TProtocolException(
                      "Received wrong type for field 'raw' (expected=%s, actual=%s).".format(
                        ttypeToString(_expectedType),
                        ttypeToString(_actualType)
                      )
                    )
                }
              case _ =>
                if (_passthroughFields == null)
                  _passthroughFields = immutable$Map.newBuilder[Short, TFieldBlob]
                _passthroughFields += (_field.id -> TFieldBlob.read(_field, _iprot))
            }
            _iprot.readFieldEnd()
          }
        }
        _iprot.readStructEnd()
    
        new Args(
          traceId,
          raw,
          if (_passthroughFields == null)
            NoPassthroughFields
          else
            _passthroughFields.result()
        )
      }
    
      def apply(
        traceId: String,
        raw: String
      ): Args =
        new Args(
          traceId,
          raw
        )
    
      def unapply(_item: Args): _root_.scala.Option[scala.Product2[String, String]] = _root_.scala.Some(_item)
    
    
      @inline private def readTraceIdValue(_iprot: TProtocol): String = {
        _iprot.readString()
      }
    
      @inline private def writeTraceIdField(traceId_item: String, _oprot: TProtocol): Unit = {
        _oprot.writeFieldBegin(TraceIdField)
        writeTraceIdValue(traceId_item, _oprot)
        _oprot.writeFieldEnd()
      }
    
      @inline private def writeTraceIdValue(traceId_item: String, _oprot: TProtocol): Unit = {
        _oprot.writeString(traceId_item)
      }
    
      @inline private def readRawValue(_iprot: TProtocol): String = {
        _iprot.readString()
      }
    
      @inline private def writeRawField(raw_item: String, _oprot: TProtocol): Unit = {
        _oprot.writeFieldBegin(RawField)
        writeRawValue(raw_item, _oprot)
        _oprot.writeFieldEnd()
      }
    
      @inline private def writeRawValue(raw_item: String, _oprot: TProtocol): Unit = {
        _oprot.writeString(raw_item)
      }
    
    
    }
    
    class Args(
        val traceId: String,
        val raw: String,
        val _passthroughFields: immutable$Map[Short, TFieldBlob])
      extends ThriftStruct
      with scala.Product2[String, String]
      with java.io.Serializable
    {
      import Args._
      def this(
        traceId: String,
        raw: String
      ) = this(
        traceId,
        raw,
        Map.empty
      )
    
      def _1 = traceId
      def _2 = raw
    
    
    
      override def write(_oprot: TProtocol): Unit = {
        Args.validate(this)
        _oprot.writeStructBegin(Struct)
        if (traceId ne null) writeTraceIdField(traceId, _oprot)
        if (raw ne null) writeRawField(raw, _oprot)
        if (_passthroughFields.nonEmpty) {
          _passthroughFields.values.foreach { _.write(_oprot) }
        }
        _oprot.writeFieldStop()
        _oprot.writeStructEnd()
      }
    
      def copy(
        traceId: String = this.traceId,
        raw: String = this.raw,
        _passthroughFields: immutable$Map[Short, TFieldBlob] = this._passthroughFields
      ): Args =
        new Args(
          traceId,
          raw,
          _passthroughFields
        )
    
      override def canEqual(other: Any): Boolean = other.isInstanceOf[Args]
    
      override def equals(other: Any): Boolean =
        canEqual(other) &&
          _root_.scala.runtime.ScalaRunTime._equals(this, other) &&
          _passthroughFields == other.asInstanceOf[Args]._passthroughFields
    
      override def hashCode: Int = _root_.scala.runtime.ScalaRunTime._hashCode(this)
    
      override def toString: String = _root_.scala.runtime.ScalaRunTime._toString(this)
    
    
      override def productArity: Int = 2
    
      override def productElement(n: Int): Any = n match {
        case 0 => this.traceId
        case 1 => this.raw
        case _ => throw new IndexOutOfBoundsException(n.toString)
      }
    
      override def productPrefix: String = "Args"
    }

    type SuccessType = thrift.EncryptResponse
    
    object Result extends ThriftStructCodec3[Result] {
      private val NoPassthroughFields = immutable$Map.empty[Short, TFieldBlob]
      val Struct = new TStruct("encrypt_result")
      val SuccessField = new TField("success", TType.STRUCT, 0)
      val SuccessFieldManifest = implicitly[Manifest[thrift.EncryptResponse]]
    
      /**
       * Field information in declaration order.
       */
      lazy val fieldInfos: scala.List[ThriftStructFieldInfo] = scala.List[ThriftStructFieldInfo](
        new ThriftStructFieldInfo(
          SuccessField,
          true,
          false,
          SuccessFieldManifest,
          _root_.scala.None,
          _root_.scala.None,
          immutable$Map.empty[String, String],
          immutable$Map.empty[String, String]
        )
      )
    
      lazy val structAnnotations: immutable$Map[String, String] =
        immutable$Map.empty[String, String]
    
      /**
       * Checks that all required fields are non-null.
       */
      def validate(_item: Result): Unit = {
      }
    
      def withoutPassthroughFields(original: Result): Result =
        new Result(
          success =
            {
              val field = original.success
              field.map { field =>
                thrift.EncryptResponse.withoutPassthroughFields(field)
              }
            }
        )
    
      override def encode(_item: Result, _oproto: TProtocol): Unit = {
        _item.write(_oproto)
      }
    
      override def decode(_iprot: TProtocol): Result = {
        var success: _root_.scala.Option[thrift.EncryptResponse] = _root_.scala.None
        var _passthroughFields: Builder[(Short, TFieldBlob), immutable$Map[Short, TFieldBlob]] = null
        var _done = false
    
        _iprot.readStructBegin()
        while (!_done) {
          val _field = _iprot.readFieldBegin()
          if (_field.`type` == TType.STOP) {
            _done = true
          } else {
            _field.id match {
              case 0 =>
                _field.`type` match {
                  case TType.STRUCT =>
                    success = _root_.scala.Some(readSuccessValue(_iprot))
                  case _actualType =>
                    val _expectedType = TType.STRUCT
                    throw new TProtocolException(
                      "Received wrong type for field 'success' (expected=%s, actual=%s).".format(
                        ttypeToString(_expectedType),
                        ttypeToString(_actualType)
                      )
                    )
                }
              case _ =>
                if (_passthroughFields == null)
                  _passthroughFields = immutable$Map.newBuilder[Short, TFieldBlob]
                _passthroughFields += (_field.id -> TFieldBlob.read(_field, _iprot))
            }
            _iprot.readFieldEnd()
          }
        }
        _iprot.readStructEnd()
    
        new Result(
          success,
          if (_passthroughFields == null)
            NoPassthroughFields
          else
            _passthroughFields.result()
        )
      }
    
      def apply(
        success: _root_.scala.Option[thrift.EncryptResponse] = _root_.scala.None
      ): Result =
        new Result(
          success
        )
    
      def unapply(_item: Result): _root_.scala.Option[_root_.scala.Option[thrift.EncryptResponse]] = _root_.scala.Some(_item.success)
    
    
      @inline private def readSuccessValue(_iprot: TProtocol): thrift.EncryptResponse = {
        thrift.EncryptResponse.decode(_iprot)
      }
    
      @inline private def writeSuccessField(success_item: thrift.EncryptResponse, _oprot: TProtocol): Unit = {
        _oprot.writeFieldBegin(SuccessField)
        writeSuccessValue(success_item, _oprot)
        _oprot.writeFieldEnd()
      }
    
      @inline private def writeSuccessValue(success_item: thrift.EncryptResponse, _oprot: TProtocol): Unit = {
        success_item.write(_oprot)
      }
    
    
    }
    
    class Result(
        val success: _root_.scala.Option[thrift.EncryptResponse],
        val _passthroughFields: immutable$Map[Short, TFieldBlob])
      extends ThriftResponse[thrift.EncryptResponse] with ThriftStruct
      with scala.Product1[Option[thrift.EncryptResponse]]
      with java.io.Serializable
    {
      import Result._
      def this(
        success: _root_.scala.Option[thrift.EncryptResponse] = _root_.scala.None
      ) = this(
        success,
        Map.empty
      )
    
      def _1 = success
    
      def successField: Option[thrift.EncryptResponse] = success
      def exceptionFields: Iterable[Option[com.twitter.scrooge.ThriftException]] = Seq()
    
    
      override def write(_oprot: TProtocol): Unit = {
        Result.validate(this)
        _oprot.writeStructBegin(Struct)
        if (success.isDefined) writeSuccessField(success.get, _oprot)
        if (_passthroughFields.nonEmpty) {
          _passthroughFields.values.foreach { _.write(_oprot) }
        }
        _oprot.writeFieldStop()
        _oprot.writeStructEnd()
      }
    
      def copy(
        success: _root_.scala.Option[thrift.EncryptResponse] = this.success,
        _passthroughFields: immutable$Map[Short, TFieldBlob] = this._passthroughFields
      ): Result =
        new Result(
          success,
          _passthroughFields
        )
    
      override def canEqual(other: Any): Boolean = other.isInstanceOf[Result]
    
      override def equals(other: Any): Boolean =
        canEqual(other) &&
          _root_.scala.runtime.ScalaRunTime._equals(this, other) &&
          _passthroughFields == other.asInstanceOf[Result]._passthroughFields
    
      override def hashCode: Int = _root_.scala.runtime.ScalaRunTime._hashCode(this)
    
      override def toString: String = _root_.scala.runtime.ScalaRunTime._toString(this)
    
    
      override def productArity: Int = 1
    
      override def productElement(n: Int): Any = n match {
        case 0 => this.success
        case _ => throw new IndexOutOfBoundsException(n.toString)
      }
    
      override def productPrefix: String = "Result"
    }

    type FunctionType = Function1[Args,Future[thrift.EncryptResponse]]
    type ServiceType = com.twitter.finagle.Service[Args, Result]

    private[this] val toResult = (res: SuccessType) => Result(Some(res))

    def functionToService(f: FunctionType): ServiceType = {
      com.twitter.finagle.Service.mk { args: Args =>
        f(args).map(toResult)
      }
    }

    def serviceToFunction(svc: ServiceType): FunctionType = { args: Args =>
      ThriftServiceIface.resultFilter(this).andThen(svc).apply(args)
    }

    val name = "encrypt"
    val serviceName = "EdServiceEndpoint"
    val argsCodec = Args
    val responseCodec = Result
    val oneway = false
  }

  // Compatibility aliases.
  val encrypt$args = Encrypt.Args
  type encrypt$args = Encrypt.Args

  val encrypt$result = Encrypt.Result
  type encrypt$result = Encrypt.Result

  object Decrypt extends com.twitter.scrooge.ThriftMethod {
    
    object Args extends ThriftStructCodec3[Args] {
      private val NoPassthroughFields = immutable$Map.empty[Short, TFieldBlob]
      val Struct = new TStruct("decrypt_args")
      val TraceIdField = new TField("traceId", TType.STRING, 1)
      val TraceIdFieldManifest = implicitly[Manifest[String]]
      val TicketField = new TField("ticket", TType.STRING, 2)
      val TicketFieldManifest = implicitly[Manifest[String]]
    
      /**
       * Field information in declaration order.
       */
      lazy val fieldInfos: scala.List[ThriftStructFieldInfo] = scala.List[ThriftStructFieldInfo](
        new ThriftStructFieldInfo(
          TraceIdField,
          false,
          false,
          TraceIdFieldManifest,
          _root_.scala.None,
          _root_.scala.None,
          immutable$Map.empty[String, String],
          immutable$Map.empty[String, String]
        ),
        new ThriftStructFieldInfo(
          TicketField,
          false,
          false,
          TicketFieldManifest,
          _root_.scala.None,
          _root_.scala.None,
          immutable$Map.empty[String, String],
          immutable$Map.empty[String, String]
        )
      )
    
      lazy val structAnnotations: immutable$Map[String, String] =
        immutable$Map.empty[String, String]
    
      /**
       * Checks that all required fields are non-null.
       */
      def validate(_item: Args): Unit = {
      }
    
      def withoutPassthroughFields(original: Args): Args =
        new Args(
          traceId =
            {
              val field = original.traceId
              field
            },
          ticket =
            {
              val field = original.ticket
              field
            }
        )
    
      override def encode(_item: Args, _oproto: TProtocol): Unit = {
        _item.write(_oproto)
      }
    
      override def decode(_iprot: TProtocol): Args = {
        var traceId: String = null
        var ticket: String = null
        var _passthroughFields: Builder[(Short, TFieldBlob), immutable$Map[Short, TFieldBlob]] = null
        var _done = false
    
        _iprot.readStructBegin()
        while (!_done) {
          val _field = _iprot.readFieldBegin()
          if (_field.`type` == TType.STOP) {
            _done = true
          } else {
            _field.id match {
              case 1 =>
                _field.`type` match {
                  case TType.STRING =>
                    traceId = readTraceIdValue(_iprot)
                  case _actualType =>
                    val _expectedType = TType.STRING
                    throw new TProtocolException(
                      "Received wrong type for field 'traceId' (expected=%s, actual=%s).".format(
                        ttypeToString(_expectedType),
                        ttypeToString(_actualType)
                      )
                    )
                }
              case 2 =>
                _field.`type` match {
                  case TType.STRING =>
                    ticket = readTicketValue(_iprot)
                  case _actualType =>
                    val _expectedType = TType.STRING
                    throw new TProtocolException(
                      "Received wrong type for field 'ticket' (expected=%s, actual=%s).".format(
                        ttypeToString(_expectedType),
                        ttypeToString(_actualType)
                      )
                    )
                }
              case _ =>
                if (_passthroughFields == null)
                  _passthroughFields = immutable$Map.newBuilder[Short, TFieldBlob]
                _passthroughFields += (_field.id -> TFieldBlob.read(_field, _iprot))
            }
            _iprot.readFieldEnd()
          }
        }
        _iprot.readStructEnd()
    
        new Args(
          traceId,
          ticket,
          if (_passthroughFields == null)
            NoPassthroughFields
          else
            _passthroughFields.result()
        )
      }
    
      def apply(
        traceId: String,
        ticket: String
      ): Args =
        new Args(
          traceId,
          ticket
        )
    
      def unapply(_item: Args): _root_.scala.Option[scala.Product2[String, String]] = _root_.scala.Some(_item)
    
    
      @inline private def readTraceIdValue(_iprot: TProtocol): String = {
        _iprot.readString()
      }
    
      @inline private def writeTraceIdField(traceId_item: String, _oprot: TProtocol): Unit = {
        _oprot.writeFieldBegin(TraceIdField)
        writeTraceIdValue(traceId_item, _oprot)
        _oprot.writeFieldEnd()
      }
    
      @inline private def writeTraceIdValue(traceId_item: String, _oprot: TProtocol): Unit = {
        _oprot.writeString(traceId_item)
      }
    
      @inline private def readTicketValue(_iprot: TProtocol): String = {
        _iprot.readString()
      }
    
      @inline private def writeTicketField(ticket_item: String, _oprot: TProtocol): Unit = {
        _oprot.writeFieldBegin(TicketField)
        writeTicketValue(ticket_item, _oprot)
        _oprot.writeFieldEnd()
      }
    
      @inline private def writeTicketValue(ticket_item: String, _oprot: TProtocol): Unit = {
        _oprot.writeString(ticket_item)
      }
    
    
    }
    
    class Args(
        val traceId: String,
        val ticket: String,
        val _passthroughFields: immutable$Map[Short, TFieldBlob])
      extends ThriftStruct
      with scala.Product2[String, String]
      with java.io.Serializable
    {
      import Args._
      def this(
        traceId: String,
        ticket: String
      ) = this(
        traceId,
        ticket,
        Map.empty
      )
    
      def _1 = traceId
      def _2 = ticket
    
    
    
      override def write(_oprot: TProtocol): Unit = {
        Args.validate(this)
        _oprot.writeStructBegin(Struct)
        if (traceId ne null) writeTraceIdField(traceId, _oprot)
        if (ticket ne null) writeTicketField(ticket, _oprot)
        if (_passthroughFields.nonEmpty) {
          _passthroughFields.values.foreach { _.write(_oprot) }
        }
        _oprot.writeFieldStop()
        _oprot.writeStructEnd()
      }
    
      def copy(
        traceId: String = this.traceId,
        ticket: String = this.ticket,
        _passthroughFields: immutable$Map[Short, TFieldBlob] = this._passthroughFields
      ): Args =
        new Args(
          traceId,
          ticket,
          _passthroughFields
        )
    
      override def canEqual(other: Any): Boolean = other.isInstanceOf[Args]
    
      override def equals(other: Any): Boolean =
        canEqual(other) &&
          _root_.scala.runtime.ScalaRunTime._equals(this, other) &&
          _passthroughFields == other.asInstanceOf[Args]._passthroughFields
    
      override def hashCode: Int = _root_.scala.runtime.ScalaRunTime._hashCode(this)
    
      override def toString: String = _root_.scala.runtime.ScalaRunTime._toString(this)
    
    
      override def productArity: Int = 2
    
      override def productElement(n: Int): Any = n match {
        case 0 => this.traceId
        case 1 => this.ticket
        case _ => throw new IndexOutOfBoundsException(n.toString)
      }
    
      override def productPrefix: String = "Args"
    }

    type SuccessType = thrift.DecryptResponse
    
    object Result extends ThriftStructCodec3[Result] {
      private val NoPassthroughFields = immutable$Map.empty[Short, TFieldBlob]
      val Struct = new TStruct("decrypt_result")
      val SuccessField = new TField("success", TType.STRUCT, 0)
      val SuccessFieldManifest = implicitly[Manifest[thrift.DecryptResponse]]
    
      /**
       * Field information in declaration order.
       */
      lazy val fieldInfos: scala.List[ThriftStructFieldInfo] = scala.List[ThriftStructFieldInfo](
        new ThriftStructFieldInfo(
          SuccessField,
          true,
          false,
          SuccessFieldManifest,
          _root_.scala.None,
          _root_.scala.None,
          immutable$Map.empty[String, String],
          immutable$Map.empty[String, String]
        )
      )
    
      lazy val structAnnotations: immutable$Map[String, String] =
        immutable$Map.empty[String, String]
    
      /**
       * Checks that all required fields are non-null.
       */
      def validate(_item: Result): Unit = {
      }
    
      def withoutPassthroughFields(original: Result): Result =
        new Result(
          success =
            {
              val field = original.success
              field.map { field =>
                thrift.DecryptResponse.withoutPassthroughFields(field)
              }
            }
        )
    
      override def encode(_item: Result, _oproto: TProtocol): Unit = {
        _item.write(_oproto)
      }
    
      override def decode(_iprot: TProtocol): Result = {
        var success: _root_.scala.Option[thrift.DecryptResponse] = _root_.scala.None
        var _passthroughFields: Builder[(Short, TFieldBlob), immutable$Map[Short, TFieldBlob]] = null
        var _done = false
    
        _iprot.readStructBegin()
        while (!_done) {
          val _field = _iprot.readFieldBegin()
          if (_field.`type` == TType.STOP) {
            _done = true
          } else {
            _field.id match {
              case 0 =>
                _field.`type` match {
                  case TType.STRUCT =>
                    success = _root_.scala.Some(readSuccessValue(_iprot))
                  case _actualType =>
                    val _expectedType = TType.STRUCT
                    throw new TProtocolException(
                      "Received wrong type for field 'success' (expected=%s, actual=%s).".format(
                        ttypeToString(_expectedType),
                        ttypeToString(_actualType)
                      )
                    )
                }
              case _ =>
                if (_passthroughFields == null)
                  _passthroughFields = immutable$Map.newBuilder[Short, TFieldBlob]
                _passthroughFields += (_field.id -> TFieldBlob.read(_field, _iprot))
            }
            _iprot.readFieldEnd()
          }
        }
        _iprot.readStructEnd()
    
        new Result(
          success,
          if (_passthroughFields == null)
            NoPassthroughFields
          else
            _passthroughFields.result()
        )
      }
    
      def apply(
        success: _root_.scala.Option[thrift.DecryptResponse] = _root_.scala.None
      ): Result =
        new Result(
          success
        )
    
      def unapply(_item: Result): _root_.scala.Option[_root_.scala.Option[thrift.DecryptResponse]] = _root_.scala.Some(_item.success)
    
    
      @inline private def readSuccessValue(_iprot: TProtocol): thrift.DecryptResponse = {
        thrift.DecryptResponse.decode(_iprot)
      }
    
      @inline private def writeSuccessField(success_item: thrift.DecryptResponse, _oprot: TProtocol): Unit = {
        _oprot.writeFieldBegin(SuccessField)
        writeSuccessValue(success_item, _oprot)
        _oprot.writeFieldEnd()
      }
    
      @inline private def writeSuccessValue(success_item: thrift.DecryptResponse, _oprot: TProtocol): Unit = {
        success_item.write(_oprot)
      }
    
    
    }
    
    class Result(
        val success: _root_.scala.Option[thrift.DecryptResponse],
        val _passthroughFields: immutable$Map[Short, TFieldBlob])
      extends ThriftResponse[thrift.DecryptResponse] with ThriftStruct
      with scala.Product1[Option[thrift.DecryptResponse]]
      with java.io.Serializable
    {
      import Result._
      def this(
        success: _root_.scala.Option[thrift.DecryptResponse] = _root_.scala.None
      ) = this(
        success,
        Map.empty
      )
    
      def _1 = success
    
      def successField: Option[thrift.DecryptResponse] = success
      def exceptionFields: Iterable[Option[com.twitter.scrooge.ThriftException]] = Seq()
    
    
      override def write(_oprot: TProtocol): Unit = {
        Result.validate(this)
        _oprot.writeStructBegin(Struct)
        if (success.isDefined) writeSuccessField(success.get, _oprot)
        if (_passthroughFields.nonEmpty) {
          _passthroughFields.values.foreach { _.write(_oprot) }
        }
        _oprot.writeFieldStop()
        _oprot.writeStructEnd()
      }
    
      def copy(
        success: _root_.scala.Option[thrift.DecryptResponse] = this.success,
        _passthroughFields: immutable$Map[Short, TFieldBlob] = this._passthroughFields
      ): Result =
        new Result(
          success,
          _passthroughFields
        )
    
      override def canEqual(other: Any): Boolean = other.isInstanceOf[Result]
    
      override def equals(other: Any): Boolean =
        canEqual(other) &&
          _root_.scala.runtime.ScalaRunTime._equals(this, other) &&
          _passthroughFields == other.asInstanceOf[Result]._passthroughFields
    
      override def hashCode: Int = _root_.scala.runtime.ScalaRunTime._hashCode(this)
    
      override def toString: String = _root_.scala.runtime.ScalaRunTime._toString(this)
    
    
      override def productArity: Int = 1
    
      override def productElement(n: Int): Any = n match {
        case 0 => this.success
        case _ => throw new IndexOutOfBoundsException(n.toString)
      }
    
      override def productPrefix: String = "Result"
    }

    type FunctionType = Function1[Args,Future[thrift.DecryptResponse]]
    type ServiceType = com.twitter.finagle.Service[Args, Result]

    private[this] val toResult = (res: SuccessType) => Result(Some(res))

    def functionToService(f: FunctionType): ServiceType = {
      com.twitter.finagle.Service.mk { args: Args =>
        f(args).map(toResult)
      }
    }

    def serviceToFunction(svc: ServiceType): FunctionType = { args: Args =>
      ThriftServiceIface.resultFilter(this).andThen(svc).apply(args)
    }

    val name = "decrypt"
    val serviceName = "EdServiceEndpoint"
    val argsCodec = Args
    val responseCodec = Result
    val oneway = false
  }

  // Compatibility aliases.
  val decrypt$args = Decrypt.Args
  type decrypt$args = Decrypt.Args

  val decrypt$result = Decrypt.Result
  type decrypt$result = Decrypt.Result


  trait FutureIface extends EdServiceEndpoint[Future] {
    
    def encrypt(traceId: String, raw: String): Future[thrift.EncryptResponse]
    
    def decrypt(traceId: String, ticket: String): Future[thrift.DecryptResponse]
  }

  class FinagledClient(
      service: com.twitter.finagle.Service[ThriftClientRequest, Array[Byte]],
      protocolFactory: TProtocolFactory = Protocols.binaryFactory(),
      serviceName: String = "EdServiceEndpoint",
      stats: com.twitter.finagle.stats.StatsReceiver = com.twitter.finagle.stats.NullStatsReceiver,
      responseClassifier: ctfs.ResponseClassifier = ctfs.ResponseClassifier.Default)
    extends EdServiceEndpoint$FinagleClient(
      service,
      protocolFactory,
      serviceName,
      stats,
      responseClassifier)
    with FutureIface {

    def this(
      service: com.twitter.finagle.Service[ThriftClientRequest, Array[Byte]],
      protocolFactory: TProtocolFactory,
      serviceName: String,
      stats: com.twitter.finagle.stats.StatsReceiver
    ) = this(service, protocolFactory, serviceName, stats, ctfs.ResponseClassifier.Default)
  }

  class FinagledService(
      iface: FutureIface,
      protocolFactory: TProtocolFactory)
    extends EdServiceEndpoint$FinagleService(
      iface,
      protocolFactory)
}