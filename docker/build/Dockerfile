FROM java

# Install RabbitMQ
RUN apt-get -q update && apt-get -q install -my rabbitmq-server && apt-get clean && apt-get autoclean

# Copy Files
ADD ./muss.jar /etc/muss.jar
ADD ./start.sh /etc/start.sh
RUN chmod +x /etc/start.sh

# Enable port 80
EXPOSE 80

# Execute start
CMD bash -C '/etc/start.sh';'bash'