package com.lawsofnature.edcenter.rpc

import java.util

import Ice.ObjectImpl
import com.google.inject.matcher.Matchers
import com.google.inject.name.Names
import com.google.inject.{AbstractModule, Guice}
import com.lawsofnatrue.common.cache.anno.ServiceCache
import com.lawsofnatrue.common.cache.interceptor.{CacheInterceptor, CacheInterceptorImpl}
import com.lawsofnatrue.common.ice.{ConfigHelper, IceServerTemplate, IceServerTemplateImpl}
import com.lawsofnature.common.redis.{RedisClientTemplate, RedisClientTemplateImpl}
import com.lawsofnature.edcenter.repo.{EDecryptRepository, EDecryptRepositoryImpl}
import com.lawsofnature.edcenter.service.{EdService, EdServiceImpl}
import com.typesafe.config.{Config, ConfigFactory}
import org.slf4j.LoggerFactory

object RpcService extends App {
  var logger = LoggerFactory.getLogger(this.getClass)

  private val config: Config = ConfigFactory.load()

  var redisClientTemplate: RedisClientTemplate = new RedisClientTemplateImpl(config.getString("redis.shards"),
    config.getInt("redis.shard.connection.timeout"),
    config.getInt("redis.min.idle"),
    config.getInt("redis.max.idle"),
    config.getInt("redis.max.total"),
    config.getInt("redis.max.wait.millis"),
    config.getBoolean("redis.test.on.borrow")
  )
  redisClientTemplate.init
  var cacheInterceptor:CacheInterceptor = new CacheInterceptorImpl(redisClientTemplate)

  private val injector = Guice.createInjector(new AbstractModule() {
    override def configure() {
      val map: util.HashMap[String, String] = ConfigHelper.configMap
      Names.bindProperties(binder(), map)
      bind(classOf[EDecryptRepository]).to(classOf[EDecryptRepositoryImpl]).asEagerSingleton()
      bind(classOf[EdService]).to(classOf[EdServiceImpl]).asEagerSingleton()
      bind(classOf[ObjectImpl]).to(classOf[EdServiceEndpointImpl]).asEagerSingleton()
      bind(classOf[IceServerTemplate]).to(classOf[IceServerTemplateImpl]).asEagerSingleton()
      bind(classOf[RedisClientTemplate]).to(classOf[RedisClientTemplateImpl]).asEagerSingleton()
      bindInterceptor(Matchers.any(), Matchers.annotatedWith(classOf[ServiceCache]), cacheInterceptor)
    }
  })

  injector.getInstance(classOf[IceServerTemplate]).startServer
}
