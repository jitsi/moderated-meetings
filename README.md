
# Jitsi Moderated Meetings microservice

A standalone microservice to generate join info for Jitsi Moderated Meetings.

## Development

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

## Local Image Testing

The `./docker-local.sh` script is used for local testing in a Docker image. It
expects the certificate file in the project root, named `moderated.der` (and it
has to be reflected in the env variables too), and also expects the `.env` file
to exist.

## Deployment

The `deploy` script can be used to build and deploy a docker image to AWS ECR.
Rigging associated with such a deployment is left as an exercise for the reader.

Usage is ``./deploy.sh [version] [ecr_registry] [[ecr_region]]``
