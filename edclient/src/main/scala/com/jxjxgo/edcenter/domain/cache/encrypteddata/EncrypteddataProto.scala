// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package com.jxjxgo.edcenter.domain.cache.encrypteddata



object EncrypteddataProto {
  private lazy val ProtoBytes: Array[Byte] =
      com.trueaccord.scalapb.Encoding.fromBase64(scala.collection.Seq(
  """ChdzcmMvZW5jcnlwdGVkZGF0YS5wcm90byLHAQoNRW5jcnlwdGVkRGF0YRIWCgZ0aWNrZXQYASABKAlSBnRpY2tldBISCgRoY
  XNoGAIgASgJUgRoYXNoEiAKC2VuY3J5cHRUeXBlGAMgASgJUgtlbmNyeXB0VHlwZRIeCgplbmNyeXB0S2V5GAQgASgJUgplbmNye
  XB0S2V5EiAKC2VuY3J5cHREYXRhGAUgASgJUgtlbmNyeXB0RGF0YRImCg5lbmNyeXB0VmVyc2lvbhgGIAEoBVIOZW5jcnlwdFZlc
  nNpb25CIgogY29tLmp4anhnby5lZGNlbnRlci5kb21haW4uY2FjaGViBnByb3RvMw=="""
      ).mkString)
  lazy val scalaDescriptor: _root_.scalapb.descriptors.FileDescriptor = {
    val scalaProto = com.google.protobuf.descriptor.FileDescriptorProto.parseFrom(ProtoBytes)
    _root_.scalapb.descriptors.FileDescriptor.buildFrom(scalaProto, Seq(
    ))
  }
  lazy val javaDescriptor: com.google.protobuf.Descriptors.FileDescriptor = {
    val javaProto = com.google.protobuf.DescriptorProtos.FileDescriptorProto.parseFrom(ProtoBytes)
    com.google.protobuf.Descriptors.FileDescriptor.buildFrom(javaProto, Array(
    ))
  }
  @deprecated("Use javaDescriptor instead. In a future version this will refer to scalaDescriptor.", "ScalaPB 0.5.47")
  def descriptor: com.google.protobuf.Descriptors.FileDescriptor = javaDescriptor
}