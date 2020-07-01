import i18n from 'i18next';
import languages from 'languages';
import { initReactI18next } from 'react-i18next';

/**
 * Initializer function for i18n translations.
 */
export function initI18n(): Promise<void> {
    return new Promise(resolve => {
        i18n.use(initReactI18next).init({
            resources: languages,
            fallbackLng: 'en'
        }, resolve);
    });
}
