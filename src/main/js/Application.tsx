import { createBrowserHistory } from 'history';
import React, { PureComponent, ReactNode } from 'react';
import { Route, Router, Switch } from 'react-router-dom';
import Home from 'screens/Home';
import Join from 'screens/Join';

/**
 * The main application class of the app.
 */
export default class Application extends PureComponent {
    /**
     * Implements PureComponent#render.
     *
     * @inheritdoc
     */
    render(): ReactNode {
        return (
            <Router history = { createBrowserHistory() }>
                <Switch>
                    <Route path = '/:meetingId'>
                        <Join />
                    </Route>
                    <Route path = '/'>
                        <Home />
                    </Route>
                </Switch>
            </Router>
        );
    }
}
