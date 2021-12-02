JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Client.java \
	HandleClient.java \
	Utilities.java \
	Server.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

server:
	@echo "Running Server..."
	@java Server

client:
	@echo "Running Client..."
	@java Client
	