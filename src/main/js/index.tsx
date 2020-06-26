import '../scss/index.scss';

import Application from 'Application';
import ConfigContext from 'components/ConfigContext';
import analytics from 'functions/analytics';
import { get } from 'functions/restUtils';
import Config from 'model/Config';
import React from 'react';
import ReactDOM from 'react-dom';

get('/rest/config').then((config: Config) => {
    analytics.init(config).then(() => {
        ReactDOM.render(<ConfigContext.Provider value = { config }>
            <Application />
        </ConfigContext.Provider>, document.getElementById('jitsi-moderated-meetings'));
    });
});
