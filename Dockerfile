# DOCKER-VERSION 1.8.2
FROM       ubuntu:trusty
MAINTAINER Pascal Grimaud <pascalgrimaud@gmail.com>

ENV JAVA_VERSION 8
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

ENV MAVEN_VERSION 3.3.3
ENV MAVEN_HOME /usr/share/maven
ENV PATH "$PATH:$MAVEN_HOME/bin"

# install utilities
RUN apt-get -y install vim git sudo zip bzip2 fontconfig curl

# install maven
RUN curl -fsSL http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar xzf - -C /usr/share \
    && mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
    && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

# install java8
RUN echo 'deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main' >> /etc/apt/sources.list && \
    echo 'deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main' >> /etc/apt/sources.list && \
    apt-key adv --keyserver keyserver.ubuntu.com --recv-keys C2518248EEA14886 && \
    apt-get update && \
    echo oracle-java${JAVA_VERSION}-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections && \
    apt-get install -y --force-yes --no-install-recommends oracle-java${JAVA_VERSION}-installer oracle-java${JAVA_VERSION}-set-default

# install node.js
RUN curl -sL https://deb.nodesource.com/setup_4.x | sudo bash -
RUN apt-get install -y nodejs unzip python g++ build-essential

# install bower grunt
RUN npm install -g bower grunt-cli

# configure the "jhipster" user
RUN groupadd jhipster && useradd jhipster -s /bin/bash -m -g jhipster -G jhipster && adduser jhipster sudo
RUN echo 'jhipster:jhipster' |chpasswd
RUN chown -R jhipster:jhipster /home/jhipster

# clone project
USER jhipster
RUN cd /home/jhipster && \
    git clone https://github.com/pascalgrimaud/hello-jhipster.git && \
    cd /home/jhipster/hello-jhipster && \
    mvn -Pprod package -DskipTests=true && \
    mv /home/jhipster/hello-jhipster/target/hello-jhipster-2.23.0.war /home/jhipster/hello-jhipster-2.23.0.war && \
    rm -Rf /home/jhipster/hello-jhipster && \
    rm -Rf /home/jhipster/.m2/repository

RUN ls -al /home/jhipster

EXPOSE 8080
CMD ["bash"]
