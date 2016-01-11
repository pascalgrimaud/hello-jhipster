#!/bin/sh
################################################################################
# MySQL
################################################################################
if [ -d ${SPRING_DATASOURCE_URL} ]; then
  SPRING_DATASOURCE_URL="jdbc:mysql://${MYSQL_PORT_3306_TCP_ADDR}:${MYSQL_PORT_3306_TCP_PORT}/HelloJhipster?useUnicode=true&characterEncoding=utf8"
  echo "SPRING_DATASOURCE_URL autoconfigured by docker link: ${SPRING_DATASOURCE_URL}"
else
  echo "SPRING_DATASOURCE_URL init by configuration: ${SPRING_DATASOURCE_URL}"
fi
################################################################################
# ElasticSearch
################################################################################
if [ -d ${SPRING_DATA_ELASTICSEARCH_CLUSTER_NODES} ]; then
  SPRING_DATA_ELASTICSEARCH_CLUSTER_NODES="${ELASTIC_PORT_9300_TCP_ADDR}:${ELASTIC_PORT_9300_TCP_PORT}"
  echo "SPRING_DATA_ELASTICSEARCH_CLUSTER_NODES autoconfigured by docker link: ${SPRING_DATA_ELASTICSEARCH_CLUSTER_NODES}"
else
  echo "SPRING_DATA_ELASTICSEARCH_CLUSTER_NODES init by configuration: ${SPRING_DATA_ELASTICSEARCH_CLUSTER_NODES}"
fi
if [ -d ${JHIPSTER_SLEEP} ]; then
  JHIPSTER_SLEEP=20
fi
################################################################################
# Start application
################################################################################
echo "The application will start in ${JHIPSTER_SLEEP}sec..." && sleep ${JHIPSTER_SLEEP}
if [ -d ${JHIPSTER_SPRING} ]; then
  java -jar /app.war \
    --spring.profiles.active=prod ${JHIPSTER_SPRING_ADD} \
    --spring.data.elasticsearch.cluster-nodes=${SPRING_DATA_ELASTICSEARCH_CLUSTER_NODES} \
    --spring.datasource.url=${SPRING_DATASOURCE_URL}
else
  echo "java -jar /app.war ${JHIPSTER_SPRING}"
  java -jar /app.war ${JHIPSTER_SPRING}
fi
