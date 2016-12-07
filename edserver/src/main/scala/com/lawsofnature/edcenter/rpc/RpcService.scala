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
import org.slf4j.LoggerFactory

object RpcService extends App {
  var logger = LoggerFactory.getLogger(this.getClass)

  private val interceptorInjector = Guice.createInjector(new AbstractModule() {
    override def configure() {
      val map: util.HashMap[String, String] = ConfigHelper.configMap
      Names.bindProperties(binder(), map)
      bind(classOf[RedisClientTemplate]).to(classOf[RedisClientTemplateImpl]).asEagerSingleton()
      bind(classOf[CacheInterceptor]).to(classOf[CacheInterceptorImpl]).asEagerSingleton()
    }
  })

  private val injector = Guice.createInjector(new AbstractModule() {
    override def configure() {
      val map: util.HashMap[String, String] = ConfigHelper.configMap
      Names.bindProperties(binder(), map)
      bind(classOf[EDecryptRepository]).to(classOf[EDecryptRepositoryImpl]).asEagerSingleton()
      bind(classOf[EdService]).to(classOf[EdServiceImpl]).asEagerSingleton()
      bind(classOf[ObjectImpl]).to(classOf[EdServiceEndpointImpl]).asEagerSingleton()
      bind(classOf[IceServerTemplate]).to(classOf[IceServerTemplateImpl]).asEagerSingleton()
      bind(classOf[CacheInterceptor]).to(classOf[CacheInterceptorImpl]).asEagerSingleton()
      bind(classOf[RedisClientTemplate]).to(classOf[RedisClientTemplateImpl]).asEagerSingleton()
      bindInterceptor(Matchers.any(), Matchers.annotatedWith(classOf[ServiceCache]), interceptorInjector.getInstance(classOf[CacheInterceptor]))
    }
  })

  injector.getInstance(classOf[IceServerTemplate]).startServer
  injector.getInstance(classOf[RedisClientTemplate]).init
}
