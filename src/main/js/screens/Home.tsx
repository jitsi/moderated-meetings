import Screen, { Props as AbstractProps, State as AbstractState } from 'components/Screen';
import { parseQueryParams } from 'functions/urlUtils';
import React, { ReactNode } from 'react';
import { withRouter, RouteComponentProps } from 'react-router-dom';

/**
 * Type interface for the possible query params of the page.
 */
interface QueryParams {

    /**
     * If true, the app auto generates the links, so the user doesn't have to
     * click the button.
     */
    autoGenerate?: string;
}

/**
 * Type interface for the Props of the component.
 */
interface Props extends AbstractProps, RouteComponentProps {}

/**
 * Type interface for the State of the component.
 */
interface State extends AbstractState {

    /**
     * The generated join URL, if any.
     */
    joinUrl?: string;

    /**
     * The generated moderator join URL, if any.
     */
    moderatorUrl?: string;

    /**
     * True of the generate button should be rendered, false otherwise.
     */
    showHome: boolean;
}

/**
 * Screen that implements the home page of the app.
 */
class Home extends Screen<Props, State> {
    /**
     * Query params of the page.
     */
    params: QueryParams;

    /**
     * Instantiates a new component.
     *
     * @inheritdoc
     */
    constructor(props: Props) {
        super(props);

        this.params = parseQueryParams(this.props.location.search) as QueryParams;

        this.state = {
            showHome: !this.params.hasOwnProperty('autoGenerate')
        };

        this.onGenerateMeeting = this.onGenerateMeeting.bind(this);
    }

    /**
     * Implements PureComponent#componentDidMount.
     *
     * @inheritdoc
     */
    public componentDidMount(): void {
        if (this.params.hasOwnProperty('autoGenerate')) {
            this.onGenerateMeeting();
        }
    }

    /**
     * Implements Screen#renderContent.
     *
     * @inheritdoc
     */
    public renderContent(): ReactNode {
        if (!this.state.showHome) {
            return null;
        }

        return (
            <div className = 'content-box-section'>
                <button
                    className = 'primary'
                    onClick = { this.onGenerateMeeting }>
                    Get me a moderated meeting!
                </button>
            </div>
        );
    }

    /**
     * Function to generate a moderated meeting. This can either be
     * run by clicking the generate button on the page or automatically
     * with the 'autoGenerate' query param.
     */
    private async onGenerateMeeting(): Promise<void> {
        const response = await (await fetch('/rest/rooms')).json();

        this.props.history.push(`/${response.meetingId}`);
    }

}

export default withRouter(Home);
