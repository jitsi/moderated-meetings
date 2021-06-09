import ConfigContext from 'components/ConfigContext';
import Config from 'model/Config';
import React, { PureComponent, ReactNode } from 'react';
import { ReactSVG } from 'react-svg';

/**
 * Supertype interface for the Props of the component.
 */
export interface Props {}


/**
 * Supertype interface for the State of the component.
 */
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
export default class Screen<P extends Props = Props, S extends State = State> extends PureComponent<P, S> {
    static contextType = ConfigContext;

    /**
     * Implements PureComponent#render.
     *
     * @inheritdoc
     */
    public render(): ReactNode {
        const config = this.context as Config;

        return (
            <>
                <div id = 'top-gradient'></div>
                <header>
                    <ReactSVG src = '/assets/jitsi-logo.svg' />
                </header>
                <main>
                    <h1 className = 'centered'>
                        Jitsi Moderated Meetings
                    </h1>
                    <p className = 'centered'>
                        Jitsi moderated meetings is a feature that lets you book a meeting
                        URL in advance where you are the only moderator.
                    </p>
                    <div id = 'content-box'>
                        { this.renderContent() }
                    </div>
                    { /*
                    Bookmarking doesn't seem to be available on JS level easily, so I comment this part out
                    but don't waqnt to remove because the styling is done for it.
                    We can probably do bookmarking through our extension later, or if an API appears for it.

                    <button
                        className = 'text'
                        id = 'bookmark-note'
                        onClick = { this.bookmark }>
                        <ReactSVG
                            id = 'bookmark-icon'
                            src = '/assets/bookmark.svg' />
                        <span>
                            Bookmark this page
                        </span>
                    </button>
                    */ }
                </main>
                <footer>
                    <div id = 'footer-wrapper'>
                        <div
                            className = 'line-wrapper'
                            id = 'mobile-wrapper'>
                            <p>
                                Jitsi on mobile – download our apps and start a meeting from anywhere
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
                            <p id = 'legal-links'>
                                <a
                                    href = 'https://jitsi.org/meet-jit-si-privacy/'
                                    rel="noreferrer"
                                    target = '_blank'>
                                    Privacy Policy
                                </a>
                                <a
                                    href = 'http://jitsi.org/meet-jit-si-terms-of-service/'
                                    rel="noreferrer"
                                    target = '_blank'>
                                    Terms &amp; Conditions
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
                            <span>
                                8x8 is a proud supporter of the Jitsi community.
                            </span>
                            <a
                                href = 'https://www.8x8.com/'
                                rel="noreferrer"
                                target = '_blank'>
                                <ReactSVG src = '/assets/8x8.svg' />
                            </a>
                            <span>
                                © 8x8, Inc. All Rights Reserved.
                            </span>
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
