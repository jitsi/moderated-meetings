FROM debian:buster as builder

RUN apt-get update && apt-get install -y curl apt-utils
RUN curl -fsSL https://deb.nodesource.com/setup_14.x | bash -
RUN apt-get update
RUN apt-get install --no-install-recommends -y maven openjdk-11-jdk nodejs

WORKDIR /opt/moderated-meetings
COPY . .
RUN npm install
RUN mvn package
RUN npm run build

FROM openjdk:11

RUN apt-get update && apt-get install --no-install-recommends -y coreutils jq

WORKDIR /apps
COPY --from=builder /opt/moderated-meetings/target/*.jar ./moderated-meetings.jar
COPY --from=builder /opt/moderated-meetings/public ./public

COPY run.sh /
CMD ["/run.sh"]
