import { Modal } from './UI/Modal';
import { Map } from './UI/Map';
import { getCoordsFromAddress, getAddressFromCoords } from './UI/Utility/Location';

class PlaceFinder {

    constructor() {
        const addressForm = document.querySelector('form');
        const locateUseBtn = document.getElementById('locate-btn');
        this.shareBtn = document.getElementById('share-btn');

        locateUseBtn.addEventListener('click', this.locateUserHander.bind(this));
        this.shareBtn.addEventListener('click', this.sharePlaceHandler);
        addressForm.addEventListener('submit', this.findAddressHandler.bind(this));

    }

    sharePlaceHandler() {
        const sharedLinkInputElement = document.getElementById('share-link');
        
        if (!navigator.clipboard) {
            sharedLinkInputElement.select();
            return;
        }

        navigator.clipboard.writeText(sharedLinkInputElement.value)
            .then(() => {
                alert('Copied into clipboard!');
            })
            .catch(err => {
                console.log(err);
                sharedLinkInputElement.select();
            });
    }

    selectPlace(coordinates, address) {
        if (this.map) {
            this.map.render(coordinates);
        } else {
            this.map = new Map(coordinates);            
        }

        this.shareBtn.disabled = false;
        const sharedLinkInputElement = document.getElementById('share-link');
        sharedLinkInputElement.value = `${location.origin}/my-place?address=${encodeURI(address)}&lat=${coordinates.lat}&lng=${coordinates.lng}`;
    }

     locateUserHander() {
        if (!navigator.geolocation) {
            alert('Location feature is not available in your browser - please use a more modern browser or maunally enter an address.')
            return;
        }

        const modal = new Modal('loading-modal-content', 'Locading location - please wait!');
        modal.show();

        navigator.geolocation.getCurrentPosition(
            async successResult => {
                const coordinates = {
                    lat: successResult.coords.latitude,
                    lng: successResult.coords.longitude
                };
                
                const address = await getAddressFromCoords(coordinates);
                modal.hide();
                this.selectPlace(coordinates, address);
            },
            error => {
                modal.hide();
                alert('Could not locate  you unfortunately. Please enter an adress manually')
            }
        );

    }

    async findAddressHandler(event) {
        event.preventDefault();
        const address = event.target.querySelector('input').value;
        if (!address || address.trim().length === 0) {
            alert('Invalid address entered - please try again!');
        }

        const modal = new Modal('loading-modal-content', 'Locading location - please wait!');
        modal.show();
        
        try {
            const coordinates = await getCoordsFromAddress(address);
            this.selectPlace(coordinates, address);
        } catch (error) {
            alert(error);
        }

        modal.hide();
    }

}

const placeFinder = new PlaceFinder();