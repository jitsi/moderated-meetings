
# Jitsi Moderated Meetings microservice

A standalone microservice to generate join info for Jitsi Moderated Meetings.

## Run

1. ``npm install``
2. Create a `.env` file with environment variables in the following format:
```
DEPLOYMENT_URL=<url to the Jitsi deployment>
PORT=<port to run the server on>
PRIVATE_KEY_FILE=<absolute path to the private key in DER format>
PRIVATE_KEY_ID=<fixed ID of the private key>
TARGET_TENANT=<the tenant that was set up to be moderated on the Jitsi server configured with DEPLOYMENT_URL>
```
3. ``npm start``
4. Open ``http://localhost:[PORT]/``

Note:
- please see package.json for further npm run scripts.
- the `npm start` script expects the `.env` file to exist, but the app can be ran with out that too. For details, see the `start` script in `package.json`.

## Docker

There is a `Dockerfile` in the project root that is supposed to build a proper docker image with a two-step build process.
There is also a `docker.sh` that acts as a template script to build and run the docker image based off the `Dockerfile` content.
The `docker.sh` script expects the certificate file in the project root, named `moderated.der` (and it has to be reflected in the env variables too), and also expects the `.env` file to exist.