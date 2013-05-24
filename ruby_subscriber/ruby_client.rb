require 'dbus'
require './handler'

bus = DBus::SessionBus.instance
service = bus.request_service("dbus_demo.ruby_client")

# export the handler method the publisher expects to see
handler = Handler.new("/") # pass in the path the publisher knows to look for
service.export(handler)

# get a connection to the publisher and register
publisher = bus.service("dbus_demo.publisher").object("/")
publisher.introspect
publisher.default_iface = "dbus_demo.Publisher"
publisher.register("dbus_demo.ruby_client")

puts 'about to start looping for messages'
# listen for dbus messages
loop = DBus::Main.new
loop << bus
loop.run