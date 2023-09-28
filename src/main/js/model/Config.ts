/**
 * Interface to store the config that was downloaded from the server.
 */
interface Config {
    amplitudeKey?: string;
    fbLink?: string;
    githubLink?: string;
    linkedInLink?: string;
    twitterLink?: string;
    appStoreLink?: string;
    fdriodLink?: string;
    playStoreLink?: string;
    tokenAuthUrl?: string;
}

export default Config;
