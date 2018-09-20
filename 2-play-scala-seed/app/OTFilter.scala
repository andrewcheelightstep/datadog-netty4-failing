
import javax.inject.Inject
import akka.stream.Materializer
import com.lightstep.tracer.jre.JRETracer
import play.api.Logger
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import io.opentracing.util.GlobalTracer
import org.slf4j.LoggerFactory
import com.lightstep.tracer.shared.AbstractTracer
import play.mvc.Http

class TracingFilter @Inject() (implicit val mat: Materializer, ec: ExecutionContext) extends Filter {
  private val logger = LoggerFactory.getLogger(getClass)
  def apply(nextFilter: RequestHeader => Future[Result])
           (requestHeader: RequestHeader): Future[Result] = {

    val traceid = Option(requestHeader.headers.get("ot-traceid"))
    GlobalTracer.get().buildSpan("play").withTag("test", "true").startActive(false)
    logger.warn(GlobalTracer.get().activeSpan().toString)
    val tracer = GlobalTracer.get().asInstanceOf[JRETracer]
    tracer.flush(10000)
    nextFilter(requestHeader).map { result =>
      result.withHeaders("x-traced" -> "true")
    }
  }
}