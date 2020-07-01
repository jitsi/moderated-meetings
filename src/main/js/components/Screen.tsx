import ConfigContext from 'components/ConfigContext';
import Config from 'model/Config';
import React, { PureComponent, ReactNode } from 'react';
import { WithTranslation } from 'react-i18next';
import { ReactSVG } from 'react-svg';

/**
 * Supertype interface for the Props of the component.
 */
export interface Props extends WithTranslation {}

export interface State {}

/**
 * Social links to be rendered in the footer.
 */
const SOCIAL_LINKS = {
    facebook: 'https://www.facebook.com/jitsi',
    github: 'https://github.com/jitsi',
    linkedin: 'https://www.linkedin.com/groups/133669',
    twitter: 'https://twitter.com/jitsinews'
};

/**
 * Abstract class that implements the screen type component that all screens in the app should be derived from.
 */
export default abstract class Screen<P extends Props = Props, S extends State = State> extends PureComponent<P, S> {
    static contextType = ConfigContext;

    /**
     * Implements PureComponent#render.
     *
     * @inheritdoc
     */
    public render(): ReactNode {
        const config = this.context as Config;
        const { t } = this.props;

        return (
            <>
                <header>
                    <ReactSVG src = '/assets/jitsi-logo.svg' />
                    <div id = 'header-description'>
                        <h1 className = 'centered'>
                            { t('header.title') }
                        </h1>
                        <p className = 'centered'>
                            { t('header.subTitle') }
                        </p>
                    </div>
                </header>
                <main>
                    { this.renderContent() }
                </main>
                <footer>
                    <div id = 'footer-wrapper'>
                        <div
                            className = 'line-wrapper'
                            id = 'mobile-wrapper'>
                            <p>
                                { t('footer.mobileLinksDescription') }
                            </p>
                            <div id = 'mobile-links'>
                                <a
                                    className = 'store-badge-wrapper'
                                    href = {
                                        config.appStoreLink
                                        || 'https://apps.apple.com/us/app/jitsi-meet/id1165103905'
                                    }
                                    rel="noreferrer"
                                    target = '_blank'>
                                    <img src = '/assets/appstore.png' />
                                </a>
                                <a
                                    className = 'store-badge-wrapper'
                                    href = {
                                        config.playStoreLink
                                        || 'https://play.google.com/store/apps/details?id=org.jitsi.meet'
                                    }
                                    rel="noreferrer"
                                    target = '_blank'>
                                    <img src = '/assets/playstore.png' />
                                </a>
                                <a
                                    className = 'store-badge-wrapper'
                                    href = {
                                        config.fdriodLink
                                        || 'https://f-droid.org/en/packages/org.jitsi.meet'
                                    }
                                    rel="noreferrer"
                                    target = '_blank'>
                                    <img src = '/assets/fdroid.png' />
                                </a>
                            </div>
                        </div>
                        <hr />
                        <div className = 'line-wrapper'>
                            <p>
                                { t('footer.slackLinksDescription') }
                            </p>
                            <div>
                                <a
                                    className = 'store-badge-wrapper'
                                    href = {
                                        config.slackLink
                                        || 'https://scraperapi.slack.com/apps/A3LN30B7T-jitsi-meet'
                                    }
                                    rel="noreferrer"
                                    target = '_blank'>
                                    <img src = '/assets/slack.png' />
                                </a>
                            </div>
                        </div>
                        <hr />
                        <div className = 'line-wrapper'>
                            <p id = 'legal-links'>
                                <a
                                    href = 'https://jitsi.org/meet-jit-si-privacy/'
                                    rel="noreferrer"
                                    target = '_blank'>
                                    { t('footer.privacyPolicy') }
                                </a>
                                <a
                                    href = 'http://jitsi.org/meet-jit-si-terms-of-service/'
                                    rel="noreferrer"
                                    target = '_blank'>
                                    { t('footer.termsNConditions') }
                                </a>
                            </p>
                            <div id = 'social-wrapper'>
                                <a href = { config.fbLink || SOCIAL_LINKS.facebook }>
                                    <ReactSVG
                                        src = '/assets/facebook.svg' />
                                </a>
                                <a href = { config.githubLink || SOCIAL_LINKS.github }>
                                    <ReactSVG
                                        src = '/assets/github.svg' />
                                </a>
                                <a href = { config.linkedInLink || SOCIAL_LINKS.linkedin }>
                                    <ReactSVG
                                        src = '/assets/linkedin.svg' />
                                </a>
                                <a href = { config.twitterLink || SOCIAL_LINKS.twitter }>
                                    <ReactSVG
                                        src = '/assets/twitter.svg' />
                                </a>
                            </div>
                        </div>
                        <hr />
                        <div
                            className = 'line-wrapper'
                            id = 'copyright'>
                            <a
                                href = 'https://www.8x8.com/'
                                rel="noreferrer"
                                target = '_blank'>
                                <ReactSVG src = '/assets/8x8.svg' />
                            </a>
                            <div>
                                <span>
                                    { t('footer.legalNote1') }
                                </span>
                                <span>
                                    { t('footer.legalNote2') }
                                </span>
                            </div>
                        </div>
                    </div>
                </footer>
            </>
        );
    }

    /**
     * Function to be implemented by the child class to render the dynamic part of the screen.
     */
    renderContent(): ReactNode {
        return null;
    }
}
