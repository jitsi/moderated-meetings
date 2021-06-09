# local test script
docker build --tag moderated:SNAPSHOT .
docker run --rm --publish 8001:8080 --name moderated-meetings --mount type=bind,source=$PWD/moderated.der,target=/usr/share/moderated-meetings/moderated.der --env-file .env moderated:SNAPSHOT
