
# Jitsi Moderated Meetings microservice

A standalone microservice to generate join info for Jitsi Moderated Meetings.

## Run

1. ``npm install``
2. Set the environment variables according to the following:
```
DEPLOYMENT_URL=<url to the Jitsi deployment>
PORT=<port to run the server on>
PRIVATE_KEY_FILE=<absolute path to the private key in DER format>
PRIVATE_KEY_ID=<fixed ID of the private key>
TARGET_TENANT=<the tenant that was set up to be moderated on the Jitsi server configured with DEPLOYMENT_URL>
```
3. ``npm start``
4. Open ``http://localhost:[PORT]/``

Note: please see package.json for further npm run scripts.
