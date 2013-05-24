package dbus_demo.client

import org.freedesktop.dbus.DBusInterface

trait Handler extends DBusInterface {

    def handle(message: String)

}
