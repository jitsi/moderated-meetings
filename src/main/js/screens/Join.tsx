import Screen, { Props as AbstractProps, State as AbstractState } from 'components/Screen';
import analytics from 'functions/analytics';
import { get } from 'functions/restUtils';
import React, { ReactNode } from 'react';
import { withRouter, RouteComponentProps } from 'react-router-dom';
import { ReactSVG } from 'react-svg';

/**
 * Type interface for the URL params of the page.
 */
interface URLParams {
    meetingId: string;
}

/**
 * Type interface for the Props of the component.
 */
interface Props extends AbstractProps, RouteComponentProps<URLParams> {}

/**
 * Type interface for the State of the component.
 */
interface State extends AbstractState {
    joinUrl?: string | undefined;
    moderatorUrl?: string | undefined;
}

/**
 * Screen that implements the join page of the app.
 */
class Join extends Screen<Props, State> {
    /**
     * Instantiates a new component.
     *
     * @inheritdoc
     */
    constructor(props: Props) {
        super(props);

        this.state = {};

        this.joinAsModerator = this.joinAsModerator.bind(this);
    }

    /**
     * Implements PureComponent#componentDidMount.
     *
     * @inheritdoc
     */
    async componentDidMount(): Promise<void> {
        const { meetingId } = this.props.match.params;
        const { joinUrl, moderatorUrl } = await get(`/rest/rooms/${meetingId}`);

        this.setState({
            joinUrl,
            moderatorUrl
        });

        analytics.sendAnalyticsEvent('screen:join');
    }

    /**
     * Implements Screen#renderContent.
     *
     * @inheritdoc
     */
    public renderContent(): ReactNode {
        const { joinUrl } = this.state;

        if (!joinUrl) {
            return null;
        }

        return (
            <>
                <div className = 'content-box-section'>
                    <label htmlFor = 'joinUrl'>
                        Share meeting link for guests
                    </label>
                    <div className = 'copy-field-wrapper'>
                        <input
                            id = 'joinUrl'
                            readOnly = { true }
                            type = 'text'
                            value = { joinUrl} />
                        <button
                            className = 'text'
                            onClick = { this.copyUrl('joinUrl') }>
                            <ReactSVG src = '/assets/copy.svg'
                                beforeInjection={(svg) => {
                                    svg.setAttribute('fill', 'white')
                                }}
                            />
                        </button>
                    </div>
                </div>
                <hr />
                <div className = 'content-box-section'>
                    <label htmlFor = 'moderatorUrl'>
                        Share this page with other moderators
                    </label>
                    <div className = 'copy-field-wrapper'>
                        <input
                            id = 'moderatorUrl'
                            readOnly = { true }
                            type = 'text'
                            value = { document.location.href } />
                        <button
                            className = 'text'
                            onClick = { this.copyUrl('moderatorUrl') }>
                            <ReactSVG src = '/assets/copy.svg'
                                beforeInjection={(svg) => {
                                    svg.setAttribute('fill', 'white')
                                }}
                            />
                        </button>
                    </div>
                    <button
                        className = 'primary'
                        onClick = { this.joinAsModerator }>
                        Join as moderator
                    </button>
                </div>
            </>
        );
    }

    /**
     * Callback to be invoked on clicking one of the copy buttons.
     *
     * @param fieldId The field ID to copy content from.
     */
    private copyUrl(fieldId: string): () => void {
        return (): void => {
            const copyText = document.getElementById(fieldId) as HTMLInputElement;

            copyText.select();

            // Workaround for mobile devices
            copyText.setSelectionRange(0, 99999);
            document.execCommand('copy');
            copyText.setSelectionRange(0, 0);
            copyText.blur();

            analytics.sendAnalyticsEvent('action:copy-url', { field: fieldId });
        };
    }

    /**
     * Callback to be invoked when the user clicks the join as moderator button.
     */
    private joinAsModerator(): void {
        const { moderatorUrl } = this.state;

        if (moderatorUrl) {
            document.location.href = moderatorUrl;
        }

        analytics.sendAnalyticsEvent('action:join-moderator');
    }

}

export default withRouter(Join);
