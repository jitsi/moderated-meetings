
# Jitsi Moderated Meetings microservice

A standalone microservice to generate join info for Jitsi Moderated Meetings.

## Run

1. ``npm install``
2. Create a properties file under ``src/main/resources/config.properties`` with the following content:
```
deployment.url=<url to the Jitsi deployment>
private.key.file=<absolute path to the private key in DER format>
private.key.id=<fixed ID of the private key>
server.port=<port to run the server on>
target.tenant=<the tenant that was set up to be moderated on the backend>
```
3. ``npm start``
4. Open ``http://localhost:[PORT]/``

Note: please see package.json for further npm run scripts.
