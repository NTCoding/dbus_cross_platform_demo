package dbus_demo.client

import org.freedesktop.dbus.DBusInterface

class Handler extends DBusInterface {

    def handle(message: String) {
        println(f"Scala client received $message")
    }

    def isRemote: Boolean = false

}
