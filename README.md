
# Jitsi Moderated Meetings microservice

Example of a standalone microservice that can generate tokens for Jitsi
Moderated Meetings.

## Usage

1. ``npm install``
2. Generate a keypair to sign JWT tokens. The public key will be used by
   `prosody` to validated moderated tokens, as described in:
   https://github.com/jitsi/lib-jitsi-meet/blob/master/doc/tokens.md
3. Create a `.env` file with environment variables in the following format:
```
DEPLOYMENT_URL=<url to the Jitsi deployment with / at the end>
PORT=<port to run this Spring Boot application on>
PRIVATE_KEY_FILE=<absolute path to the private key in DER format>
PRIVATE_KEY_ID=<fixed ID of the private key, this will be the kid of the generated tokens>
TARGET_TENANT=<the tenant to be moderated; URLs will begin with https://[DEPLOYMENT_URL]/[TARGET_TENANT]>
```

To generate the der file used for `PRIVATE_KEY_FILE`:
```
openssl rsa -inform pem -in jitsi-private.pem -outform der -out PrivateKey.der
```

4. ``npm start``
5. Open ``http://localhost:[PORT]/``

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
