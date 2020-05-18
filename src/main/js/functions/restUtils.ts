/**
 * Wrapper to fetch URLs with common logic. May be extended later.
 *
 * @param url The URL to fetch.
 */
export async function get(url: string): Promise<any> {
    return await (await fetch(url)).json();
}
