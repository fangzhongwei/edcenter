package com.jxjxgo.edcenter.rpc

import java.util

import com.google.inject.matcher.Matchers
import com.google.inject.name.Names
import com.google.inject.{AbstractModule, Guice}
import com.jxjxgo.common.cache.anno.ServiceCache
import com.jxjxgo.common.cache.interceptor.{CacheInterceptor, CacheInterceptorImpl}
import com.jxjxgo.common.helper.ConfigHelper
import com.jxjxgo.common.redis.{RedisClientTemplate, RedisClientTemplateImpl}
import com.jxjxgo.edcenter.repo.{EDecryptRepository, EDecryptRepositoryImpl}
import com.jxjxgo.edcenter.service.{EdService, EdServiceImpl}
import com.jxjxgo.scrooge.thrift.template.{ScroogeThriftServerTemplate, ScroogeThriftServerTemplateImpl}
import com.twitter.scrooge.ThriftService
import com.typesafe.config.{Config, ConfigFactory}
import org.slf4j.LoggerFactory

object RpcService extends App {
  private[this] val logger = LoggerFactory.getLogger(this.getClass)

  private[this] val config: Config = ConfigFactory.load()

  var redisClientTemplate: RedisClientTemplate = new RedisClientTemplateImpl(config.getString("redis.shards"),
    config.getInt("redis.shard.connection.timeout"),
    config.getInt("redis.min.idle"),
    config.getInt("redis.max.idle"),
    config.getInt("redis.max.total"),
    config.getInt("redis.max.wait.millis"),
    config.getBoolean("redis.test.on.borrow")
  )
  redisClientTemplate.init
  private[this] val cacheInterceptor: CacheInterceptor = new CacheInterceptorImpl(redisClientTemplate)

  private[this] val injector = Guice.createInjector(new AbstractModule() {
    override def configure() {
      val map: util.HashMap[String, String] = ConfigHelper.configMap
      Names.bindProperties(binder(), map)
      bind(classOf[EDecryptRepository]).to(classOf[EDecryptRepositoryImpl]).asEagerSingleton()
      bind(classOf[EdService]).to(classOf[EdServiceImpl]).asEagerSingleton()
      bind(classOf[ThriftService]).to(classOf[EdServiceEndpointImpl]).asEagerSingleton()
      bind(classOf[ScroogeThriftServerTemplate]).to(classOf[ScroogeThriftServerTemplateImpl]).asEagerSingleton()
      bind(classOf[RedisClientTemplate]).to(classOf[RedisClientTemplateImpl]).asEagerSingleton()
      bindInterceptor(Matchers.any(), Matchers.annotatedWith(classOf[ServiceCache]), cacheInterceptor)
    }
  })

  injector.getInstance(classOf[ScroogeThriftServerTemplate]).init
}
