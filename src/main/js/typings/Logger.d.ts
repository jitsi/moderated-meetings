declare module 'jitsi-meet-logger' {

    /**
     * Class replresents a logger in any JS/TS jitsi applications.
     */
    class Logger {
        info(message: string, ...params): void;
    }

    export function getLogger(id: string, transports?: Array<unknown> | undefined, options?: unknown): Logger;
}
