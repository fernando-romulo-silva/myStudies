class DOMHelper {
    static moveElement(elementId, newDestinationSelector) {
        const element = document.getElementById(elementId);
        const destinationElement = document.querySelector(newDestinationSelector);
        destinationElement.append(element);
    }

    static clearEventListener(element) {
        const clonedElement = element.cloneNode(true);
        element.replaceWith(clonedElement);

        return clonedElement;
    }
}

class Component {

    constructor(hostElementId, insertBefore = false) {
        if (hostElementId) {
            this.hostElement = document.getElementById(hostElementId);
        } else {
            this.hostElement = document.body;
        }

        this.insertBefore = insertBefore;
    }

    detach () {

        if (this.element) {            
            this.element.remove();
        }

        // this.element.parentElement.removeChild(this.element);
    }

    attach () {
        this.hostElement.insertAdjacentElement(
            this.insertBefore ? 'afterbegin' : 'beforeend', 
            this.element);
    }
}

class Tooltip extends Component {

    constructor(closeNotifierFunction) {
        super();

        this.closeNotifier = closeNotifierFunction;
        this.create();
    }

    closeTooltip = () => {
        this.detach();
    }

    create() {
        const tooltipElement = document.createElement('div');
        tooltipElement.className = 'card';
        tooltipElement.textContent = 'DUMMY!';  

        tooltipElement.addEventListener('click', this.closeTooltip);
        this.element = tooltipElement;
    }
}

class ProjectItem {

    hasActiveTooltip = false;

    constructor(id, updateProjectListsFunction, type) {
        this.id = id;
        this.updateProjectListsHandler = updateProjectListsFunction;
        this.connectSwitchButton(type);
        this.connectMoreInfoButton();
    }

    connectSwitchButton(type){
        const projectItemElement = document.getElementById(this.id);
        
        let switchBtn = projectItemElement.querySelector('button:last-of-type');
        switchBtn = DOMHelper.clearEventListener(switchBtn);
        switchBtn.textContent = type === 'active' ? 'Finish' : 'Activate';

        switchBtn.addEventListener('click', this.updateProjectListsHandler.bind(null, this.id));
    }

    showMoreInfoHandler(){

        if (this.hasActiveTooltip) {
            return;
        }

        const tooltip = new Tooltip(() => {
            this.hasActiveTooltip = true;
        });

        tooltip.attach();
        this.hasActiveTooltip = true;
    }

    connectMoreInfoButton() {
        const projectItemElement = document.getElementById(this.id);
        const moreInfoBtn = projectItemElement.querySelector('button:first-of-type');
        
        moreInfoBtn.addEventListener('click', this.showMoreInfoHandler);
        
    }

    update(updateProjectListFn, type) {
        this.updateProjectListsHandler = updateProjectListFn;
        this.connectSwitchButton(type);
    }

}

class ProjectList {

    projects = [];

    constructor(type) {

        this.type = type;

        const prjItems = document.querySelectorAll(`#${type}-projects li`);

        for (const prjItem of prjItems) {
            this.projects.push(new ProjectItem(prjItem.id, this.switchProject.bind(this)), this.type);
        }

        console.log(this.projects);
    }

    setSwitchHandlerFunction(switchHandlerFunction) {
        this.switchHandler = switchHandlerFunction;
    }

    addProject(project) {
        this.projects.push(project);
        
        console.log(this);
        
        DOMHelper.moveElement(project.id, `#${this.type}-projects ul`);
        project.update(this.switchProject.bind(this), this.type);
    }

    switchProject(projectId) {
        // const projectIndex = this.projects.findIndex(p => p.id === projectId);
        // this.projects.slice(projectIndex, 1);

        this.switchHandler(this.projects.find(p => p.id === projectId));
        
        this.projects = this.projects.filter(p => p.id !== projectId);
    }

}

class App {

    static init() {
        const activeProjectsList = new ProjectList('active');
        const finishedProjectsList = new ProjectList('finished');

        activeProjectsList.setSwitchHandlerFunction(finishedProjectsList.addProject.bind(finishedProjectsList));

        finishedProjectsList.setSwitchHandlerFunction(activeProjectsList.addProject.bind(activeProjectsList));
    }
}

App.init();