package dbus_demo

import org.freedesktop.dbus.{DBusInterface, DBusConnection}
import dbus_demo.client.Handler

object PublisherApp {

  def main(args: Array[String]) {
    val connection = DBusConnection.getConnection(DBusConnection.SESSION)
    connection.requestBusName("dbus_demo.publisher")
    connection.exportObject("/", new Publisher())

    Thread.sleep(1000 * 300)   // keep the application running
  }
}

class Publisher extends DBusInterface {

    def isRemote: Boolean = false

    def publish(message: String) {
        println(f"Publisher about to publish $message")
        PublisherHelper.subscribers.foreach(s => PublisherHelper.publishToSubscriber(s, message))
    }

    def register(serviceName: String) {
        println(f"Publisher received subscription request from $serviceName")
        println(f"Publisher adding $serviceName to list of subscribers")
        PublisherHelper.addSubscriber(serviceName)
        println(f"$serviceName added to list of subscribers")
        println(f"Current subscribers: ")
        PublisherHelper.subscribers.foreach(println)
    }

}

object PublisherHelper {

    var subscribers: Array[String] = Array[String]()

    def addSubscriber(name: String) {
        subscribers = subscribers :+ name
    }

    def publishToSubscriber(serviceName: String, message: String) {
        println(f"About to publish $message to $serviceName")

        val connection = DBusConnection.getConnection(DBusConnection.SESSION)
        connection.getRemoteObject(serviceName, "/", classOf[Handler]) match {
            case handler: Handler => handler.handle(message)
        }

        println(f"message sent to $serviceName")
    }
}
