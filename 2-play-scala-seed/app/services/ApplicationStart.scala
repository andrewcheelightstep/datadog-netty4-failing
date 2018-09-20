package services

import io.opentracing.util.GlobalTracer
import javax.inject._
import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future

@Singleton
class ApplicationStart @Inject() (lifecycle: ApplicationLifecycle) {
  val tracer = new com.lightstep.tracer.jre.JRETracer(
    new com.lightstep.tracer.shared.Options.OptionsBuilder()
      .withAccessToken("c5d967d063bd96e478b5c73f7db125a0")
      .withCollectorHost("collector-grpc-staging.lightstep.com")
      .withCollectorPort(443)
      .withCollectorProtocol("https")
      .build()
  )
  GlobalTracer.register(tracer)

  lifecycle.addStopHook { () =>
    tracer.flush(30000)
    Future.successful(())
  }
}