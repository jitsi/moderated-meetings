/**
 * Function to parse query params of a page.
 *
 * @param query The query string returned by location.search
 * (See: {@link https://reacttraining.com/react-router/web/api/location}).
 */
export function parseQueryParams(query: string = ''): Record<string, unknown> {
    const parsedQueryParams = {};
    const params = query.substr(query.startsWith('?') ? 1 : 0).split('&');

    for (const param of params) {
        const [ key, value ] = param.split('=');

        if (key) {
            parsedQueryParams[key] = value;
        }
    }

    return parsedQueryParams;
}
