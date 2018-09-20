
import com.google.inject.AbstractModule
import services.ApplicationStart

class Module extends AbstractModule {
  override def configure() = {
    bind(classOf[ApplicationStart]).asEagerSingleton()
  }
}