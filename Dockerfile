ARG JITSI_REPO=jitsi

FROM debian:stretch as builder

RUN apt-get update && apt-get install -y curl apt-utils
RUN curl -sL https://deb.nodesource.com/setup_12.x | bash -
RUN apt-get update
RUN apt-get install --no-install-recommends -y maven openjdk-8-jdk nodejs

WORKDIR /opt/moderated-meetings
COPY . .
RUN mvn package
RUN npm run build

FROM ${JITSI_REPO}/base-java

WORKDIR /usr/share/moderated-meetings/
COPY --from=builder /opt/moderated-meetings/target/*.jar ./moderated-meetings.jar
COPY --from=builder /opt/moderated-meetings/public ./public

CMD java -jar moderated-meetings.jar
