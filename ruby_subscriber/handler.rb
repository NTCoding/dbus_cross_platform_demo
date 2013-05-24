require 'dbus'

class Handler < DBus::Object
	dbus_interface "dbus_demo.client.Handler" do # dbus_demo.client is interface publisher looks for
		dbus_method :handle , "in message:s" do |message|
			puts "dbus ruby client received message #{message}"
		end
	end
end

