package dbus_demo

import org.freedesktop.dbus.{DBusInterface, DBusConnection}
import dbus_demo.client.Handler

object ScalaClient {

    def main(args: Array[String]) {

        val connection = DBusConnection.getConnection(DBusConnection.SESSION)

        connection.requestBusName("dbus_demo.scala_client")

        // tell dbus we implement the handler interface
        connection.exportObject("/", new Handler())

        // create a proxy object matching publisher's interface and send register method
        // arguments are service name, path, interface
        connection.getRemoteObject("dbus_demo.publisher", "/", classOf[Publisher]) match {
            case p: Publisher => p.register("dbus_demo.scala_client")
        }

        Thread.sleep(1000 * 300)   // keep the application running
    }
}


// representation of implementation that lives in the publisher application
trait Publisher extends DBusInterface {

    def publish(message: String)

    def register(serviceName: String)

}
