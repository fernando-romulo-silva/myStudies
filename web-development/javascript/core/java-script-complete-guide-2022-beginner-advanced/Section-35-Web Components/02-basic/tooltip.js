class Tooltip extends HTMLElement {

    constructor() {
        super();
        this._tooltipContainer;
        this._tootipText = 'Some dummy tooltip text.';
        this.attachShadow({ mode: 'open' });

        // const template = document.querySelector('#tooltip-template');
        // this.shadowRoot.appendChild(template.content.cloneNode(true));

        this.shadowRoot.innerHTML = `
            <style>
                div {
                    background-color: black;
                    color: white;
                    position: absolute;
                    z-index: 10;
                }

                :host(.important) {
                    background: var(--color-primary);
                }

                :host-context(p) {
                    font-weight: bold;
                }

                .highlight {
                    background-color: red;                    
                }

                ::slotted(.highlight) {
                    border-bottom: 1px dotted red;
                }

                .icon {
                    background: black;
                    color: white;
                    padding: 0.25rem 0.5rem;
                    text-align: center;
                    border-radius: 50%;
                }

            </style>
            <slot>Some default</slot>
            <span class="icon"> (?)</span>
        `;
    }
    
    connectedCallback() {
        
        if (this.hasAttribute('text')) {
            this._tootipText = this.getAttribute('text');
        }
        
        const tooltipIcon = this.shadowRoot.querySelector('span');
        
        tooltipIcon.addEventListener('mouseenter', this._showTooltip.bind(this));
        tooltipIcon.addEventListener('mouseleave', this._hideTooltip.bind(this));

        this.style.position = 'relative';
        
        this.shadowRoot.appendChild(tooltipIcon);
    }

    _showTooltip() {
        this._tooltipContainer = document.createElement('div');
        this._tooltipContainer.textContent = this._tootipText;

        // You don't need anymore!
        // this._tooltipContainer.style.background = 'black';
        // this._tooltipContainer.style.color = 'white';
        // this._tooltipContainer.style.position = 'absolute';
        // this._tooltipContainer.style.zIndex = '10';

        this.shadowRoot.appendChild(this._tooltipContainer);
    }

    _hideTooltip() {
        this.shadowRoot.removeChild(this._tooltipContainer);
    }

}

customElements.define('uc-tooltip', Tooltip);