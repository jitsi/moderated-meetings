import analytics from 'functions/analytics';
import React, { PureComponent, ReactNode } from 'react';
import { ReactSVG } from 'react-svg';
import { uuid } from 'uuidv4';

interface Props {

    /**
     * Label of the field to be rendered.
     */
    label: string;

    /**
     * The read-only value of the field.
     */
    value: string;
}

/**
 * Implements a read only field component that has built in copy-content functionality.
 */
export default class CopiableField extends PureComponent<Props> {
    fieldId: string;
    wrapperId: string;

    /**
     * Instantiates a new component.
     *
     * @inheritdoc
     */
    constructor(props: Props) {
        super(props);

        const id = uuid();

        this.fieldId = `field-${id}`;
        this.wrapperId = `wrapper-${id}`;

        this.copyValue = this.copyValue.bind(this);
    }

    /**
     * Implements PureComponent#render.
     *
     * @inheritdoc
     */
    public render(): ReactNode {
        return (
            <div className = 'copiable-field'>
                <label htmlFor = { this.fieldId }>
                    { this.props.label }
                </label>
                <div
                    className='copy-field-wrapper'
                    id = { this.wrapperId }>
                    <button onClick = { this.copyValue }>
                        <input
                            id = { this.fieldId }
                            readOnly = { true }
                            type = 'text'
                            value = { this.props.value } />

                        { /* Label that gets rendered on hover */ }
                        <span className = 'copy-message'>
                            Copy meeting link
                        </span>

                        <ReactSVG
                            className = 'copy-icon'
                            src = '/assets/copy.svg' />

                        { /* Copied message that gets rendered for a few seconds after clicking the button */ }
                        <div className = 'copied-message'>
                            <span>
                                Link copied to clipboard
                            </span>
                            <ReactSVG src = '/assets/check.svg' />
                        </div>
                    </button>
                </div>
            </div>
        );
    }

    /**
     * Callback for the copy field value button.
     */
    private copyValue(): void {
        const copyText = document.getElementById(this.fieldId) as HTMLInputElement;

        copyText.select();

        // Workaround for mobile devices
        copyText.setSelectionRange(0, 99999);
        document.execCommand('copy');
        copyText.setSelectionRange(0, 0);
        copyText.blur();

        analytics.sendAnalyticsEvent('action:copy-url');

        const wrapper = document.getElementById(this.wrapperId);

        if (wrapper) {
            wrapper.classList.add('copied');

            setTimeout(() => {
                wrapper.classList.remove('copied');
            }, 3000);
        }
    }
}
