download:
	wget https://github.com/kwhat/jnativehook/releases/download/2.2.2/jnativehook-2.2.2.jar
build:
	javac -cp "jnativehook-2.2.2.jar" Server.java KeyLogger.java -d .
	javac Client.java
server:
	java -cp ".;jnativehook-2.2.2.jar" keystroke.Server
client:
	java Client
clean:
	rm -fr keystroke
	rm -fr *.class
	rm -fr *.dll
	rm -fr *.so
