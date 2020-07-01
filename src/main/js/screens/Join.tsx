import CopiableField from 'components/CopiableField';
import Screen, { Props as AbstractProps, State as AbstractState } from 'components/Screen';
import analytics from 'functions/analytics';
import { get } from 'functions/restUtils';
import React, { ReactNode } from 'react';
import { withRouter, RouteComponentProps } from 'react-router-dom';

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
                    <CopiableField
                        label = 'Share meeting link for guests'
                        value = { joinUrl } />
                </div>
                <hr className = 'main-separator' />
                <div className = 'content-box-section'>
                    <CopiableField
                        label = 'Share this page with other moderators'
                        value = { document.location.href } />
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
