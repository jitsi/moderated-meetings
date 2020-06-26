import amplitude, { AmplitudeClient } from 'amplitude-js';
import logger from 'functions/logger';
import Config from 'model/Config';

/**
 * Singleton class that encapsulates the interaction with a potential analytics handler.
 */
class Analytics {
    amplitudeInstance: AmplitudeClient = amplitude.getInstance();
    analyticsEnabled: boolean = false;

    /**
     * Initializes the analytics handler, if it is set up in the passed config.
     *
     * @param config The congif object downloaded form the backend.
     */
    init(config: Config): Promise<void> {
        return new Promise(resolve => {
            if (config.amplitudeKey) {
                // Key is provided, so we init the analytics SDK
                this.amplitudeInstance.init(config.amplitudeKey, undefined, undefined, () => {
                    this.analyticsEnabled = true;
                    logger.info('Analytics initialized.');
                    resolve();
                });
            } else {
                logger.info('Analytics are disabled.');
                resolve();
            }
        });
    }

    /**
     * Function to send an analytics event.
     *
     * @param name The name of the event to send.
     * @param meta Optional metadata (object) to be sent with the event.
     */
    sendAnalyticsEvent(name: string, meta?: unknown): void {
        if (this.analyticsEnabled) {
            this.amplitudeInstance.logEvent(name, meta);
        }
    }

}

export default new Analytics();
