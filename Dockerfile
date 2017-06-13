FROM flangelier/scala

RUN wget https://github.com/Antonio21MP/Lenguajes/archive/master.zip
RUN unzip master.zip

EXPOSE 8080

CMD cd Lenguajes-master/Scala && scalac server.scala && scala Server