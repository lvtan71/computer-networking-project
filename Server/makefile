download:
	wget -P libs -q https://github.com/kwhat/jnativehook/releases/download/2.2.2/jnativehook-2.2.2.jar
compile:
	javac -cp ".;libs/jnativehook-2.2.2.jar" src/*.java -d bin
run:
	java -cp ".;bin;libs/jnativehook-2.2.2.jar" src.Server
